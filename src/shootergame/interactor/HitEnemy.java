/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.interactor;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.Sensor;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import city.cs.engine.Shape;
import city.cs.engine.SoundClip;
import city.cs.engine.World;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jbox2d.common.Vec2;
import shootergame.filemanager.OptionManager;
import shootergame.weapon.gun.MachineGun;
import shootergame.weapon.gun.Pistol;
import shootergame.weapon.gun.Shotgun;

/**
 * The Enemy that follows the player and attacks at close proximity. Parent class
 * is Enemy.
 * 
 * @author Tahmid
 */
public class HitEnemy extends Enemy implements SensorListener, CollisionListener
{
    private Vec2 speed = new Vec2(5,0);
    //private int damage = 10;
    
    //Sensor
    private Sensor sensor;
    private Shape sensorShape;
    private float xDirection;
    
    
    private Player player;
    
    private Timer timer;
    
    private long lastWait = 0;
    private long waitTime = 2000;
    
    private SoundClip hitSound;
    private double masterVolume;
    
    //Images
    private BodyImage leftWalk = new BodyImage("data/Enemy/Hit/Walk/flipWalk.gif",3f);
    private BodyImage rightWalk = new BodyImage("data/Enemy/Hit/Walk/Walk.gif",3f);
    private BodyImage leftAttack = new BodyImage("data/Enemy/Hit/Attack/flipAttack.gif",3.5f);
    private BodyImage rightAttack = new BodyImage("data/Enemy/Hit/Attack/Attack.gif",3.5f);
    
    /**
     *
     * @param world
     * World the Enemy will be in.
     * @param player
     * Player the enemy will attack.
     * @param mg
     * MG weapon.
     * @param p
     * Pistol Weapon.
     * @param s
     * Shotgun Weapon.
     * @throws UnsupportedAudioFileException
     * If an unsupported audio file is played.
     * 
     * @throws IOException
     * If there's an error with the IO.
     * 
     * @throws LineUnavailableException
     * when .getLine() fails.
     */
    public HitEnemy(World world, Player player, MachineGun mg, Pistol p, Shotgun s)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        super(world, player, mg, p, s, 300, 30,"data/Enemy/Hit/Idle/Idle.gif","data/Enemy/Hit/Idle/flipIdle.gif");
        
        //Sensor proximity
        sensorShape = new BoxShape(20f, 1f);
        sensor = new Sensor(this, sensorShape);
        sensor.addSensorListener(this);
        
        this.player = player;
        xDirection = player.getPosition().x - this.getPosition().x;
        
        OptionManager optionsManager = new OptionManager();
        optionsManager.read();
        masterVolume = Double.parseDouble(optionsManager.getLine(0).substring(8));
        
        openSound();
    }
    
    @Override
    public void beginContact(SensorEvent e)
    {
        super.beginContact(e);
        //this.removeAttachedImage(image);
        
        
        if(e.getContactBody() == player)
        {
            timer = new Timer();
            timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    xDirection = player.getPosition().x - getPosition().x;
                    if(xDirection < -3f)
                    {
                        removeAllImages();
                        addImage(leftWalk);
                        startWalking(-2f);
                    }
                    else if(xDirection >= -3f && xDirection <= 0)
                    {
                        removeAllImages();
                        stopWalking();
                        addImage(leftAttack);
                        if((System.currentTimeMillis()-lastWait) > waitTime)
                        {
                            if(masterVolume > 0)
                            {
                                hitSound.play();
                            }
                            player.setHealth(player.getHealth()-25);
                            player.applyForce(new Vec2(-1000,10000));
                            lastWait = System.currentTimeMillis();
                        }
                        
                    }
                    
                    if(xDirection > 3f)
                    {
                        removeAllImages();
                        addImage(rightWalk);
                        startWalking(2f);
                    }
                    else if(xDirection <= 3f && xDirection > 0)
                    {
                        removeAllImages();
                        stopWalking();
                        addImage(rightAttack);
                        if((System.currentTimeMillis()-lastWait) > waitTime)
                        {
                            if(masterVolume > 0)
                            {
                                hitSound.play();
                            }
                            player.setHealth(player.getHealth()-25);
                            player.applyForce(new Vec2(100,100));
                            lastWait = System.currentTimeMillis();
                        }
                    }
                }
                
            }, 500,500);
        }
    }
    
    @Override
    public void endContact(SensorEvent e)
    {
        super.endContact(e);
        System.out.println(e.getContactBody());
        System.out.println("END");
        if(e.getContactBody() == player){
            try{
                timer.cancel();
                stopWalking();
            }catch(NullPointerException pe){}
            if(getDirection().equals("left"))
            {
                removeAllImages();
                addImage(getLeft());
            }
            
            if(getDirection().equals("right"))
            {
                removeAllImages();
                addImage(getRight());
            }
        }
    }
    
    @Override
    public void collide(CollisionEvent e)
    {
        super.collide(e); //To change body of generated methods, choose Tools | Templates.
        if(this.getHealth() <= 0)
        {
            try{
            sensor.removeAllSensorListeners();
            timer.cancel();
            }catch(NullPointerException npe){}
        }
    }
    
    /**
     * Close Sound. Will need to be reopened if you want to reuse.
     */
    public void stopSound()
    {
        if(masterVolume >= 0)
        {
            hitSound.close();
        }
    }
    
    /**
     * pause sound.
     */
    public void pauseSound()
    {
        if(masterVolume >= 0)
        {
            hitSound.pause();
        }
    }
    
    /**
     * Reopen sound clips.
     * 
     * @throws UnsupportedAudioFileException
     * If an unsupported audio file is played.
     * 
     * @throws IOException
     * If there's an error with the IO.
     * 
     * @throws LineUnavailableException
     * when .getLine() fails.
     */
    public void openSound()
            throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        if(masterVolume > 0){
            hitSound = new SoundClip("data/Sound/PlayerHit/hit.wav");
        }
    }
}
