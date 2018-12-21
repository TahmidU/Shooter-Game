/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.interactor;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;
import city.cs.engine.Sensor;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;


/**
 * An interactive prop that the player can climb. A ladder.
 * 
 * @author Tahmid
 */
public class Ladder extends StaticBody implements SensorListener
{
    
    private final Shape ladderShape = new BoxShape(0.5f,0.5f);
    private Sensor sensor;
    
    private Player player;

    /**
     * Constructor for ladder.
     * 
     * @param world
     * World the ladders will be in.
     * @param player
     * Player object the ladders will interact with.
     * @param position
     * Position of the ladders.
     */
    public Ladder(World world, Player player, Vec2 position)
    {
        super(world);
        
        this.player = player;

        
        addImage(new BodyImage("data/Props/ladder.png"));
        sensor = new Sensor(this, ladderShape);
        sensor.addSensorListener(this);
        this.setPosition(position);

        
    }
    
    /**
     *
     * @return
     * Sensor
     */
    public Sensor getSensor() {
        return sensor;
    }
    
    /**
     * When the Player comes into contact with the ladder sensors the player can 
     * then use the E key to scale up the ladder.
     * 
     * @param e
     * SensorEvent
     */
    @Override
    public void beginContact(SensorEvent e)
    {
        if(e.getContactBody() == player && e.getSensor() == this.getSensor())
        {
            player.enableLadder();
        }
        
    }
    
    /**
     * When the Player loses contact with the ladder sensors the player can 
     * no longer use the E key to scale up the ladder.
     * 
     * @param e
     * SensorEvent
     */
    @Override
    public void endContact(SensorEvent e)
    {
        if(e.getContactBody() == player && e.getSensor() == this.getSensor())
        {
            player.disableLadder();
        }
    }
    
}
