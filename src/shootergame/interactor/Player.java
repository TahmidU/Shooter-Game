/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.interactor;

import city.cs.engine.BodyImage;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.SoundClip;
import city.cs.engine.UserView;
import city.cs.engine.Walker;
import city.cs.engine.World;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.jbox2d.common.Vec2;
import shootergame.filemanager.OptionManager;
import shootergame.weapon.EnemyBullet;

/**
 * The Player class allows the user to control the player object.
 * 
 * @author Tahmid
 */
public class Player extends Walker implements CollisionListener{
    
    //Basic properties of Player
    private UserView view;
    private static final Shape playerShape = new PolygonShape(-0.6f,1.46f, 0.11f,1.45f, 0.51f
            ,0.64f, 0.51f,-1.48f, -1.12f,-1.48f, -1.12f,0.64f);
    
    //Player Controls mapping
    private InputMap im;
    private ActionMap am;
    private ArrayList<Character> currentChar; //Contains the entered keys
    
    
    
    
    //Player properties
    private int health = 100;
    private String gun = "";
    private int speed = 10;
    private Vec2 startPos;
    
    //Objective
    private int enemiesKilled = 0;
    
    //Sound
    private SoundClip hitSound;
    private double masterVolume;
    
    //Player Images
    //MG
    private BodyImage RightPlayerMG = new BodyImage("data/MG/runMG.gif", 3f);
    private BodyImage LeftPlayerMG = new BodyImage("data/MG/FlipRunMG.gif", 3f);
    private BodyImage RightUpMG = new BodyImage("data/MG/jumpMG.png", 3f);
    private BodyImage LeftUpMG = new BodyImage("data/MG/FlipJumpMG.png", 3f);
    private BodyImage RightIdleMG = new BodyImage("data/MG/idleMG.gif", 3f);
    private BodyImage LeftIdleMG = new BodyImage("data/MG/FlipIdleMG.gif", 3f);
    
    //P
    private BodyImage RightPlayerP = new BodyImage("data/P/runP.gif", 3f);
    private BodyImage LeftPlayerP = new BodyImage("data/P/FlipRunP.gif", 3f);
    private BodyImage RightUpP = new BodyImage("data/P/jumpP.png", 3f);
    private BodyImage LeftUpP = new BodyImage("data/P/FlipJumpP.png", 3f);
    private BodyImage RightIdleP = new BodyImage("data/P/idleP.gif", 3f);
    private BodyImage LeftIdleP = new BodyImage("data/P/FlipIdleP.gif", 3f);
    
    //S
    private BodyImage RightPlayerS = new BodyImage("data/S/runS.gif", 3f);
    private BodyImage LeftPlayerS = new BodyImage("data/S/FlipRunS.gif", 3f);
    private BodyImage RightUpS = new BodyImage("data/S/jumpS.png", 3f);
    private BodyImage LeftUpS = new BodyImage("data/S/FlipJumpS.png", 3f);
    private BodyImage RightIdleS = new BodyImage("data/S/idleS.gif", 3f);
    private BodyImage LeftIdleS = new BodyImage("data/S/FlipIdleS.gif", 3f);
    

    /**
     * Constructor for player.
     * 
     * @param world
     * The World the player will be in.
     * @param view
     * Focus view for input and action map.
     * @param startGun
     * Starting Weapon 
     * @throws FileNotFoundException
     * Audio Not Found.
     * @throws UnsupportedEncodingException
     * Audio Not Supported.
     * @throws IOException
     * Error with IO.
     */
    public Player(World world, UserView view, String startGun)
            throws FileNotFoundException, UnsupportedEncodingException, IOException
    {
        super(world, playerShape);
        this.currentChar = new ArrayList<Character>();
        this.view = view;
        
        OptionManager optionsManager = new OptionManager();
        optionsManager.read();
        masterVolume = Double.parseDouble(optionsManager.getLine(0).substring(8));
        
        switch (startGun) {
            case "mg":
                addImage(RightIdleMG);
                break;
            case "p":
                addImage(RightIdleP);
                break;
            case "s":
                addImage(RightIdleS);
                break;
            default:
                break;
        }
        this.gun = startGun;
        this.currentChar.add('d');
        
        
        this.addCollisionListener(this);
        
        try {
            if(masterVolume > 0){
                hitSound = new SoundClip("data/Sound/PlayerHit/hit.wav");
                hitSound.setVolume(masterVolume);
            }
        } catch (UnsupportedAudioFileException| IllegalArgumentException ex){}
        catch (IOException ex) {}
        catch (LineUnavailableException ex) {}
        
        //Mapping
        im = this.view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        am = this.view.getActionMap();
        
    }
    
    //Basic Player Controls

    /**
     * Handles the player controls.
     */
    public void keyBinding()
    {
        
        
        //When Keys are pressed...
        //Basic player controls
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0 , false), "RightPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "LeftPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "UpPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W,0,false), "UpPressed");
        
        //When Keys are released
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0 , true), "RightReleased");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "LeftReleased");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "UpReleased");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W,0,true), "UpReleased");
        
        //Do this when key pressed...
        am.put("RightPressed", new AbstractAction()
        {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                startWalking(speed);
                removeAllImages();
                //cameraMove();
                //view.setCentre(new Vec2(getPlayerXPos(),0));
                switch (gun) {
                    case "mg":
                        addImage(RightPlayerMG);
                        break;
                    case "p":
                        addImage(RightPlayerP);
                        break;
                    case "s":
                        addImage(RightPlayerS);
                        break;
                    default:
                        break;
                }
                //addImage(RightPlayer);
                
                currentChar.add('d');
                
            }
            
            
        });
        
        am.put("LeftPressed", new AbstractAction()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                startWalking(-speed);
                removeAllImages();
                //cameraMove();
                //view.setCentre(new Vec2(getPlayerXPos(),0));
                switch (gun) {
                    case "mg":
                        addImage(LeftPlayerMG);
                        break;
                    case "p":
                        addImage(LeftPlayerP);
                        break;
                    case "s":
                        addImage(LeftPlayerS);
                        break;
                    default:
                        break;
                }
                //addImage(LeftPlayer);
                
                currentChar.add('a');
                
            }
            
            
        });
        
        am.put("UpPressed", new AbstractAction()
        {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                jump(15);
                //cameraMove();
                //view.setCentre(new Vec2(getPlayerXPos(),0));
                
                if(currentChar.isEmpty() == false)
                {
                    if(currentChar.get(currentChar.size()-1) == 'd')
                    {
                        
                        removeAllImages();
                        switch (gun) {
                            case "mg":
                                addImage(RightUpMG);
                                break;
                            case "p":
                                addImage(RightUpP);
                                break;
                            case "s":
                                addImage(RightUpS);
                                break;
                            default:
                                break;
                        }
                        //addImage(RightUp);
                    }
                    else if(currentChar.get(currentChar.size()-1) == 'a')
                    {
                        removeAllImages();
                        switch (gun) {
                            case "mg":
                                addImage(LeftUpMG);
                                break;
                            case "p":
                                addImage(LeftUpP);
                                break;
                            case "s":
                                addImage(LeftUpS);
                                break;
                            default:
                                break;
                        }
                        //addImage(LeftUp);
                    }
                }
                
            }
            
            
        });
        
        //Change weapon
        am.put("", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gun = "s";
                removeAllImages();
                if(currentChar.isEmpty() == false)
                {
                    if(currentChar.get(currentChar.size()-1) == 'd')
                    {
                        
                        
                        switch (gun) {
                            case "mg":
                                addImage(RightIdleMG);
                                break;
                            case "p":
                                addImage(RightIdleP);
                                break;
                            case "s":
                                addImage(RightIdleS);
                                break;
                            default:
                                break;
                        }
                        //addImage(RightUp);
                    }
                    else if(currentChar.get(currentChar.size()-1) == 'a')
                    {
                        
                        switch (gun) {
                            case "mg":
                                addImage(LeftIdleMG);
                                break;
                            case "p":
                                addImage(LeftIdleP);
                                break;
                            case "s":
                                addImage(LeftIdleS);
                                break;
                            default:
                                break;
                        }
                    }
                }
                
            }
            
            
        });
        
        //Do this when Key is release...
        am.put("RightReleased", new AbstractAction()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                stopWalking();
                applyImpulse(new Vec2(-speed, 0));
                removeAllImages();
                switch (gun) {
                    case "mg":
                        addImage(RightIdleMG);
                        break;
                    case "p":
                        addImage(RightIdleP);
                        break;
                    case "s":
                        addImage(RightIdleS);
                        break;
                    default:
                        break;
                }
                
            }
            
            
        });
        
        am.put("LeftReleased", new AbstractAction()
        {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                stopWalking();
                applyImpulse(new Vec2(-speed, 0));
                removeAllImages();
                //cameraMove();
                //view.setCentre(new Vec2(getPlayerXPos(),0));
                switch (gun) {
                    case "mg":
                        addImage(LeftIdleMG);
                        break;
                    case "p":
                        addImage(LeftIdleP);
                        break;
                    case "s":
                        addImage(LeftIdleS);
                        break;
                    default:
                        break;
                }
                
            }
            
            
        });
        
        am.put("UpReleased", new AbstractAction()
        {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                stopWalking();
                removeAllImages();
                //cameraMove();
                //view.setCentre(new Vec2(getPlayerXPos(),0));
                if(currentChar.isEmpty() == false)
                {
                    if(currentChar.get(currentChar.size()-1) == 'd')
                    {
                        
                        removeAllImages();
                        switch (gun) {
                            case "mg":
                                addImage(RightIdleMG);
                                break;
                            case "p":
                                addImage(RightIdleP);
                                break;
                            case "s":
                                addImage(RightIdleS);
                                break;
                            default:
                                break;
                        }
                        //addImage(RightUp);
                    }
                    else if(currentChar.get(currentChar.size()-1) == 'a')
                    {
                        removeAllImages();
                        switch (gun) {
                            case "mg":
                                addImage(LeftIdleMG);
                                break;
                            case "p":
                                addImage(LeftIdleP);
                                break;
                            case "s":
                                addImage(LeftIdleS);
                                break;
                            default:
                                break;
                        }
                        //addImage(LeftUp);
                    }
                }
                
            }
            
        });
        
    }
    
    /**
     * Enable E Key to scale ladders.
     */
    public void enableLadder()
    {
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0 , false), "EPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0 , true), "EReleased");
        
        am.put("EPressed", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                //jump(speed);
                //applyForce(new Vec2(0, 26000));
                setLinearVelocity(new Vec2(0, 10));
            }
        });
        
        am.put("EReleased", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //stopWalking();
            }
        });
    }
    
    /**
     * Disable E Key.
     */
    public void disableLadder()
    {
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0 , false), "EPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0 , true), "EReleased");
        
        am.put("EPressed", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent ae){}
        });
        
        am.put("EReleased", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent ae) {}
        });
    }
    
    /**
     *
     * @param startPos
     * Starting Position
     */
    public void setStartPos(Vec2 startPos)
    {
        this.startPos = startPos;
        this.setPosition(startPos);
    }
    
    /**
     *
     * @return
     * x-axis of the player position.
     */
    public float getPlayerXPos()
    {
        return this.getPosition().x;
    }
    
    /**
     * Simple increment function that increases the enemiesKilled variable.
     */
    public void incrementEnemiesKilled()
    {
        enemiesKilled ++;
        System.out.println("Enemies Killed: " + enemiesKilled);
    }
    
    /**
     *
     * @param enemiesKilled
     * Set the EnemiesKilled.
     */
    public void setEnemiesKilled(int enemiesKilled)
    {
        this.enemiesKilled = enemiesKilled;
    }
    
    /**
     *
     * @return
     * enemiesKilled.
     */
    public int getEnemiesKilled()
    {
        return enemiesKilled;
    }
    
    /**
     * Used for the player controls and allows you to create logic to dynamically
     * change resolution.
     * 
     * @return
     * Last Char in Char List.
     */
    public char getCurrentChar()
    {
        return currentChar.get(currentChar.size()-1);
    }
    
    /**
     *
     * @param letter
     * add to the Char list
     */
    public void setCurrentChar(char letter)
    {
        this.currentChar.add(letter);
    }
    
    /**
     *
     * @return
     * Health
     */
    public int getHealth() {
        return health;
    }
    
    /**
     *
     * @param health
     * Set health
     */
    public void setHealth(int health) {
        this.health = health;
    }
    
    /**
     *
     * @return
     * The gun thats currently being used by the player.
     */
    public String getGun() {
        return gun;
    }
    
    /**
     * Set gun and get the last char pressed to load the correct image.
     * 
     * @param gun
     * set gun thats being used.
     */
    public void setGun(String gun) {
        this.gun = gun;
        removeAllImages();
        if(currentChar.isEmpty() == false)
        {
            if(currentChar.get(currentChar.size()-1) == 'd')
            {
                
                
                switch (gun) {
                    case "mg":
                        addImage(RightIdleMG);
                        break;
                    case "p":
                        addImage(RightIdleP);
                        break;
                    case "s":
                        addImage(RightIdleS);
                        break;
                    default:
                        break;
                }
                //addImage(RightUp);
            }
            else if(currentChar.get(currentChar.size()-1) == 'a')
            {
                
                switch (gun) {
                    case "mg":
                        addImage(LeftIdleMG);
                        break;
                    case "p":
                        addImage(LeftIdleP);
                        break;
                    case "s":
                        addImage(LeftIdleS);
                        break;
                    default:
                        break;
                }
                //addImage(LeftUp);
            }
        }
    }
    
    /**
     *
     * @return
     * start position
     */
    public Vec2 getStartPos() {
        return startPos;
    }
    
    /**
     *
     * @return
     * speed of player.
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * When either the Boulder or EnemyBullet collides with the player reduce player
     * health. Boulder will take 100 health (instant death). Enemy will take health depending
     * on the damage it deals.
     * 
     * @param e
     * CollisionEvent
     */
    @Override
    public void collide(CollisionEvent e)
    {
        if((e.getOtherBody().getClass() == Boulder.class)==true)
        {
            health = health - 100;
        }
        
        if((e.getOtherBody().getClass() == EnemyBullet.class) == true)
        {
            if(masterVolume > 0)
            {
                hitSound.play();
            }
            try{
                health = health - ShooterEnemy.getDamage();
            }catch(NullPointerException npe){}
        }
    }
    
    /**
     * Close sound files.
     */
    public void stopSound()
    {
        removeAllCollisionListeners();
        if(masterVolume > 0)
        {
            hitSound.close();
        }
    }
    
    /**
     * Play sound files.
     */
    public void playSound()
    {
        addCollisionListener(this);
        hitSound.play();
    }
    
    /**
     * Pause Sound files.
     */
    public void pauseSound()
    {
        removeAllCollisionListeners();
        hitSound.pause();
    }
}
