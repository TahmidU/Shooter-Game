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
import city.cs.engine.DynamicBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * Health pick-up that increments the players health by 10.
 * 
 * @author Tahmid
 */
public class HealthBox extends DynamicBody implements CollisionListener
{
    
    private Player player;
    //private HealthBox health;
    
    /**
     * Constructor for Health.
     * 
     * @param world
     * World the health pick-up is in.
     * @param player
     * The player object that can pickup the health pickup.
     * @param directory
     * directory of the image.
     * @param position
     * position the health pick-up
     */
    public HealthBox(World world, Player player, String directory, Vec2 position)
    {
        super(world,new BoxShape(0.5f,0.5f));
        this.player = player;
        //this.addCollisionListener(new HealthCollision(player, this));
        this.addCollisionListener(this);
        this.addImage(new BodyImage(directory));
        this.setPosition(position);
    }
    
    /**
     * When the player comes into contact with the health pickup the player earns
     * an additional 10 points to the player health.
     * 
     * @param e
     * CollisionEvent
     */
    @Override
    public void collide(CollisionEvent e)
    {
        //Health pickup
        if(e.getOtherBody() == player && player.getHealth() <= 90)
        {
            //When player collides add 10 to current health
            e.getReportingBody().destroy();
            player.setHealth(player.getHealth()+10);
            System.out.println(player.getHealth());
        }
        
    }
    
}
