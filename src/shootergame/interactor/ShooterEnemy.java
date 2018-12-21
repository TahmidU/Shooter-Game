/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.interactor;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.CollisionEvent;
import city.cs.engine.Sensor;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import city.cs.engine.Shape;
import city.cs.engine.SoundClip;
import city.cs.engine.World;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jbox2d.common.Vec2;
import shootergame.filemanager.OptionManager;

import shootergame.weapon.EnemyBullet;
import shootergame.weapon.gun.MachineGun;
import shootergame.weapon.gun.Pistol;
import shootergame.weapon.gun.Shotgun;

/**
 * Enemy class that shoots projectiles. Parent class is Enemy.
 * 
 * @author Tahmid
 */
public class ShooterEnemy extends Enemy implements SensorListener
{
    
    //private int damage = 5;
    
    //Sensor
    private Sensor sensor;
    private Shape sensorShape;
    //private ShooterEnemySensor direction;
    
    private float xDirection;
    private String direction;
    private Timer timer;
    
    private World world;
    private EnemyBullet bullet;
    private Vec2 bulletSpeed = new Vec2(180,0);
    private Player player;
    
    private static final int damage = 5;
    
    private SoundClip shootSound;
    private double masterVolume;
    
    /**
     * ShooterEnemy Constructor
     * 
     * @param world
     * World the ShooterEnemy will be in.
     * @param player
     * Player object the ShooterEnemy will interact with.
     * @param mg
     * MG Weapon
     * @param p
     * Pistol Weapon
     * @param s
     * Shotgun Weapon
     */
    public ShooterEnemy(World world, Player player, MachineGun mg, Pistol p, Shotgun s)
    {
        super(world, player, mg, p, s, 100, damage, "data/Enemy/Shooter/shooterRight.png"
                , "data/Enemy/Shooter/shooterLeft.png");
        
        this.world = world;
        this.player =  player;
        
        //Sensor proximity
        sensorShape = new BoxShape(15f, 1f);
        sensor = new Sensor(this, sensorShape);
        sensor.addSensorListener(this);
        
        try {
            OptionManager optionsManager = new OptionManager();
            optionsManager.read();
            masterVolume = Double.parseDouble(optionsManager.getLine(0).substring(8));
            if(masterVolume > 0){
                shootSound = new SoundClip("data/Sound/Pistol.wav");
                shootSound.setVolume(masterVolume);
            }
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(ShooterEnemy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ShooterEnemy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(ShooterEnemy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * ShooterEnemy shoots EnemyBullet. Direction of projectiles is determined
     * via the Players position and Enemies position.
     */
    public void shoot()
    {
        bullet = new EnemyBullet(world, 0.1f, 0.1f);
        bullet.setBullet(true);
        if(masterVolume > 0){
            shootSound.play();
        }
        if(getDirection().equals("right"))
        {
            bullet.setPosition(new Vec2(getPosition().x+0.5f, getPosition().y));
            bullet.setLinearVelocity(bulletSpeed);
        }
        if(getDirection().equals("left"))
        {
            
            Vec2 turnedPos = new Vec2(getPosition().x-0.5f,getPosition().y);
            bullet.setPosition(turnedPos);
            bullet.setLinearVelocity(new Vec2(-bulletSpeed.x,bulletSpeed.y));
        }
        
    }
    
    @Override
    public void beginContact(SensorEvent e)
    {
        super.beginContact(e);
        if(e.getContactBody() == player)
        {
            System.out.println("Player: " + player.getPosition() + ", Enemy: " + this.getPosition());
            xDirection = player.getPosition().x - this.getPosition().x;
            timer = new Timer();
            if(xDirection > 0)
            {
                timer.schedule(new TimerTask()
                {
                    @Override
                    public void run() {
                        shoot();
                        if(getHealth() <= 0)
                        {
                            timer.cancel();
                        }
                    }
                }, 1200,1200);
                
                
            }else if(xDirection < 0)
            {
                
                timer.schedule(new TimerTask()
                {
                    @Override
                    public void run() {
                        shoot();
                        if(getHealth() <= 0)
                        {
                            timer.cancel();
                        }
                    }
                }, 1200,1200);
            }
        }
        
    }
    
    @Override
    public void endContact(SensorEvent e)
    {
        super.endContact(e);
        
        if(e.getContactBody() == player)//if the enemy loses contact with the player...
        {
            try{
                timer.cancel();
                //shootSound.stop();
                System.out.println(e.getContactBody());
            }catch(NullPointerException npe){}
        }
    }
    
    /**
     *
     * @return
     * damage
     */
    public static int getDamage() {
        return damage;
    }
    
    @Override
    public void collide(CollisionEvent e)
    {
        super.collide(e);
        if(getHealth() <= 0)
        {
            sensor.removeAllSensorListeners();
            try
            {
                timer.cancel();
            }catch(NullPointerException ex){}
        }
    }
    
    /**
     * Close sound file.
     */
    public void stopSound()
    {
        if(masterVolume > 0)
        {
            shootSound.close();
        }
    }
    
    /**
     * Pause sound file.
     */
    public void pauseSound()
    {
        timer.cancel();
        sensor.removeAllSensorListeners();
        //shootSound.pause();
    }
    
    /**
     * Play sound file.
     */
    public void playSound()
    {
        sensor.addSensorListener(this);
        //shootSound.play();
    }
    
    /**
     * Open sound files.
     * 
     * @throws UnsupportedAudioFileException
     * Audio unsupported.
     * @throws IOException
     * IO Error
     * @throws LineUnavailableException
     * .getLine() Error.
     */
    public void openSound() 
            throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        if(masterVolume > 0){
            shootSound = new SoundClip("data/Sound/Pistol.wav");
            shootSound.setVolume(masterVolume);
        }
    }
}
