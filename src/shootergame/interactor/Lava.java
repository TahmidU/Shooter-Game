/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.interactor;

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Sensor;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import city.cs.engine.Shape;
import city.cs.engine.SoundClip;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jbox2d.common.Vec2;
import shootergame.filemanager.OptionManager;
import shootergame.world.level.LevelThree;

/**
 * This renders an array of lava. If the player or the enemy touches the lava 
 * then they will get destroyed. If the player touches the lava the game will restart
 * the level however if the enemy touches it the enemy track will increment.
 * 
 * @author Tahmid
 */
public class Lava extends StaticBody implements SensorListener
{
    
    private static final Shape lavaShape = new BoxShape(130f,1f);
    private Sensor sensor;
    private BodyImage image = new BodyImage("data/Props/lava.png");
    private AttachedImage[] attachedImages;
    private float startPosX;
    private Player player;
    private SoundClip lavaSizzle;
    private SoundClip lavaDeath;
    private double masterVolume;
    
    /**
     * Constructor for Lava.
     * 
     * @param world
     * World the Lava will be in.
     * @param player
     * Player object that will be destroyed if there's collision. 
     * @param position
     * Position of the Lava.
     * @param length
     * Amount of Lava blocks on x-axis
     * @param startPosX
     * Starting position of the lava.
     * @throws FileNotFoundException
     * If File not found.
     * @throws UnsupportedEncodingException
     * If unsupported audio file is played.
     * @throws IOException
     * Error with IO.
     */
    public Lava(World world, Player player, Vec2 position, int length, float startPosX) 
            throws FileNotFoundException, UnsupportedEncodingException, IOException
    {
        super(world,lavaShape);
        this.player = player;
        attachedImages = new AttachedImage[length];
        this.startPosX = startPosX;
        
        OptionManager optionsManager = new OptionManager();
        optionsManager.read();
        masterVolume = Double.parseDouble(optionsManager.getLine(0).substring(8));
        
        for(int i = 0; i< attachedImages.length; i++)
        {
            attachedImages[i] = new AttachedImage(this,image,2f,0f,new Vec2(startPosX,0));
            startPosX += 2;//increment the X for the next Ground Grass
        }
        sensor = new Sensor(this,lavaShape);
        sensor.addSensorListener(this);
        this.setPosition(position);
        
        try {
            if(masterVolume > 0)
            {
            lavaSizzle = new SoundClip("data/Sound/Lava/Sizzle.wav");
            lavaSizzle.setVolume(masterVolume);
            }
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Lava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Lava.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(masterVolume > 0)
        {
            lavaSizzle.loop();
        }
        try {
            if(masterVolume > 0)
            {
                lavaDeath = new SoundClip("data/Sound/Lava/LavaDeath.wav");
                lavaDeath.setVolume(masterVolume);
            }
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Lava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Lava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     * @return
     * Sensor
     */
    public Sensor getSensor()
    {
        return sensor;
    }
    
    /**
     *
     * @return
     * startPosX
     */
    public float getStartPosX()
    {
        return startPosX;
    }
    
    /**
     * When either the player or the enemy touches the lava they both will be destroyed.
     * Sound effect is played after either have touched the lava.
     * 
     * @param e
     * SensorEvent
     */
    @Override
    public void beginContact(SensorEvent e)
    {
        if(e.getContactBody() == player)
        {
            if(masterVolume > 0)
            {
                lavaDeath.play();
            }
            player.setHealth(0);
        }
        System.out.println(player.getHealth());
        
        if((e.getContactBody().getClass() == ShooterEnemy.class || e.getContactBody().getClass() == HitEnemy.class)==true)
        {
            if(masterVolume > 0)
            {
                lavaDeath.play();
            }
            e.getContactBody().destroy();
            player.incrementEnemiesKilled();
        }
    }
    
    /**
     *
     * @param e
     * SensorEvent
     */
    @Override
    public void endContact(SensorEvent e){}
    
    /**
     * Close sound files.
     */
    public void stopSound()
    {
        if(masterVolume > 0){
        try{
            lavaSizzle.close();
            lavaDeath.close();
        }catch(NullPointerException npe){}
        
        }
    }
    
    /**
     * Pause Sound.
     */
    public void pauseSound()
    {
        if(masterVolume > 0)
        {
            lavaSizzle.pause();
            lavaDeath.pause();
        }
    }
    
    /**
     * loop the lava sound file.
     */
    public void playSound()
    {
        if(masterVolume > 0)
        {
            lavaSizzle.loop();
        }
    }
    
    /**
     *
     * @throws FileNotFoundException
     * File not found.
     * @throws UnsupportedEncodingException
     * Unsupported Audio file.
     * @throws IOException
     * Error with IO.
     */
    public void openSound()
            throws FileNotFoundException, UnsupportedEncodingException, IOException
    {
        //super.playSound();
        try {
            OptionManager optionsManager = new OptionManager();
            optionsManager.read();
            masterVolume = Double.parseDouble(optionsManager.getLine(0).substring(8));
            if(masterVolume > 0)
            {
                lavaSizzle = new SoundClip("data/Sound/Lava/Sizzle.wav");
                lavaDeath = new SoundClip("data/Sound/Lava/LavaDeath.wav");
                lavaSizzle.setVolume(masterVolume);
                lavaDeath.setVolume(masterVolume);
            }
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(LevelThree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(LevelThree.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(masterVolume > 0){
            lavaSizzle.loop();
        }
    }
    
}
