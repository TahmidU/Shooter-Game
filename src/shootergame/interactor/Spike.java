/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.interactor;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Sensor;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import city.cs.engine.Shape;
import city.cs.engine.SoundClip;
import city.cs.engine.StaticBody;
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

/**
 * Procedurally reduces the health of the player depending how long the player object
 * stands on the spike.
 * 
 * @author Tahmid
 */
public class Spike extends StaticBody implements SensorListener
{
    
    private final Shape spikeShape = new BoxShape(1f,0.5f);
    private Sensor sensor;
    private Timer timer;
    private Player player;
    private int HealthReduce = -5;
    private SoundClip spikeDamage;
    private double masterVolume;
    private World world;
    
    /**
     * Construct spike prop.
     *
     * @param world
     * The world the spike is going to be placed in
     * @param player
     * Player object that the spike will damage.
     * @param position
     * Position of the spike.
     */
    public Spike(World world, Player player, Vec2 position)
    {
        super(world);
        this.player = player;
        this.world = world;
        addImage(new BodyImage("data/Props/Tilesets/spike.png")).setScale(2f);
        sensor = new Sensor(this, spikeShape);
        //sensor.addSensorListener(new SpikeSensor(player, this));
        sensor.addSensorListener(this);
        this.setPosition(position);
        try {
            OptionManager optionsManager = new OptionManager();
            optionsManager.read();
            masterVolume = Double.parseDouble(optionsManager.getLine(0).substring(8));
            if(masterVolume > 0)
            {
                spikeDamage = new SoundClip("data/Sound/PlayerHit/hit.wav");
            }
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Spike.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Spike.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Spike.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Returns the sensor
     *
     * @return
     * sensor
     */
    public Sensor getSensor()
    {
        return sensor;
    }
    
    /**
     * Beginning of contact with the player remove health
     *
     * @param e
     * SensorEvent
     */
    @Override
    public void beginContact(SensorEvent e)
    {
        
        if(e.getContactBody() == player && e.getSensor() == this.getSensor())
        {
            timer = new Timer();
            timer.schedule(new TimerTask()
            {
                @Override
                public void run() {
                    if((world.isRunning() == true)){
                        player.setHealth(player.getHealth()+ HealthReduce);
                        if(masterVolume > 0)
                        {
                            spikeDamage.play();
                        }
                    }
                    //System.out.print(player.getHealth());
                }
            }, 500,500);
            
        }
        if(masterVolume > 0)
        {
            spikeDamage.stop();
        }
    }
    
    /**
     * End of contact with the player.
     *
     * @param e
     * SensorEvent
     */
    @Override
    public void endContact(SensorEvent e)
    {
        /**
         * When the bullet hits the spikes it cause a null pointer error.
         * The reason it does this is because both have timers.
         */
        try{
            timer.cancel();
        }catch(NullPointerException pe){};
        
    }
    
    /**
     *
     * @return
     * Damage of spike
     */
    public int getHealthReduce() {
        return HealthReduce;
    }
    
    /**
     *
     * @param HealthReduce
     * set the damage of the spike object.
     */
    public void setHealthReduce(int HealthReduce) {
        this.HealthReduce = HealthReduce;
    }
    
    /**
     * Close sound files.
     */
    public void stopSound()   
    {
        spikeDamage.close();
    }
    
    /**
     *
     * @throws UnsupportedAudioFileException
     * Unsupported Audio File.
     * @throws IOException
     * Error with IO.
     * @throws LineUnavailableException
     * Error with .getLine()
     */
    public void openSound()
            throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        if(masterVolume > 0)
        {
            spikeDamage = new SoundClip("data/Sound/PlayerHit/hit.wav");
        }
    }
    
}
