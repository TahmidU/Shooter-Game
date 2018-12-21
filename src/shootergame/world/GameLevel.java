/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.world;

//int boundary = ((frame.getWidth()/10)/2)-2;

import city.cs.engine.World;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.jbox2d.common.Vec2;
import shootergame.Game;
import shootergame.filemanager.OptionManager;
import shootergame.interactor.HealthBox;
import shootergame.interactor.Player;
import shootergame.weapon.gun.MachineGun;
import shootergame.weapon.gun.Pistol;
import shootergame.weapon.gun.Shotgun;
import shootergame.world.level.LevelThree;


/**
 * Parent Class that extends World. Player and other similar objects that are being 
 * used by all other levels are set up here. 
 * 
 * @author Tahmid
 */

public abstract class GameLevel extends World{
    
    //Window
    private GameFrame frame;
    
    //Player Controls mapping
    private InputMap im;
    private ActionMap am;
    
    //World Objects
    private MachineGun machineG;
    private Pistol pistol;
    private Shotgun shotgun;
    private Player player;
    private HealthBox pickHealth;
    
    private Vec2 startPos;
    private Vec2 temp;
    
    //View
    private GameView view;
    
    private Game game;
    
    private Timer timer;
    
    /**
     * Constructor for GameLevel.
     */
    public GameLevel(){}
    
    /**
     * Populates the World with objects, enables controls and plays sound files.
     * @param game
     * Game used to restart and control level progression.
     * @param view
     * GameView used by the frame.
     * @param frame
     * Frame used by the window.
     * @throws IOException
     * IO Error.
     */
    public void populate(Game game,GameView view, GameFrame frame) throws IOException
    {
        this.view = view;
        this.frame = frame;
        this.game = game;
        

        
        im = view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        am = view.getActionMap();
        
        //When Keys are pressed...
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0 , false), "MG");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0 , false), "S");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0 , false), "P");
        
        //Creates player
        player = new Player(this, view, "mg");
        player.keyBinding(); //Enables keyboard controls on Player object
        
        System.out.println(player.getPosition());
        
        //Creates weapons
        machineG = new MachineGun(this, view, player);
        view.setMg(machineG);
        pistol = new Pistol(this, view, player);
        view.setP(pistol);
        shotgun = new Shotgun(this, view, player);
        view.setS(shotgun);
        
        //Player starts with the mg, enable controls for mg
        machineG.keyBinding();
        
        am.put("MG", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                machineG.keyBinding();//Enable mg weapon controls and override
                player.setGun("mg");
            }
        });
        
        am.put("P", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                pistol.keyBinding();//Enable p weapon controls and override
                player.setGun("p");
            }
        });
        
        am.put("S", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                shotgun.keyBinding();//Enable s weapon controls and override
                player.setGun("s");
            }
        });
        
        //tick();
    }
    
    /**
     *
     * @return
     * Player
     */
    public Player getPlayer()
    {
        return this.player;
    }
    
    /**
     *
     * @return
     * Machine Gun Weapon.
     */
    public MachineGun getMachineG() {
        return machineG;
    }
    
    /**
     *
     * @return
     * Pistol Weapon
     */
    public Pistol getPistol() {
        return pistol;
    }
    
    /**
     *
     * @return
     * Shotgun Weapon
     */
    public Shotgun getShotgun() {
        return shotgun;
    }
    
    /**
     * Update and schedule tasks using timers.
     */
    public void tick()
    {
        timer = new Timer();
        timer.schedule(new TimerTask(){
            
            @Override
            public void run()
            {
                System.out.println(player.getHealth());
                if(player.getHealth() <= 0)
                {
                    try {
                        
                        timer.cancel();
                        getGame().restartGame();
                        player.destroy();
                        player.removeAllCollisionListeners();
                        
                    } catch (IOException ex) {
                        Logger.getLogger(LevelThree.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }, 1000,1000);
    }
    
    /**
     *
     * @return
     * Starting Position.
     */
    public Vec2 getStartPos()
    {
        return startPos;
    }
    
    /**
     * Stop Sound File.
     */
    public void stopSound(){}
    
    /**
     * Pause Sound File.
     */
    public void pauseSound(){}
    
    /**
     * Play Sound File.
     */
    public void playSound(){}

    /**
     *
     * @return
     * Game
     */
    public Game getGame() 
    {
        return game;
    }
    
    /**
     * Stop Updating and Scheduling tasks.
     */
    public void stopTick()
    {
        timer.cancel();
        
    }
    
    /**
     * Open Sound File.
     * @throws FileNotFoundException
     * File not found.
     * @throws UnsupportedEncodingException
     * Audio unsupported.
     * @throws IOException
     * IO Error.
     */
    public void openSound() 
            throws FileNotFoundException, UnsupportedEncodingException, IOException{}
}
