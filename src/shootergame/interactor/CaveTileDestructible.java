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
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * These are destructible blocks. If the Boulder hits them they get destroyed.
 * Can also be assigned health therefore can be hit multiple times until health
 * runs out and the block gets destroyed.
 * 
 * @author Tahmid
 */
public class CaveTileDestructible extends StaticBody implements CollisionListener
{
        
    private int health = 100;
    
    /**
     *
     * @param world
     * World the block will be placed in.
     * @param position
     * Position of the block. 
     * 
     */
    public CaveTileDestructible(World world, Vec2 position) 
    {
        super(world, new BoxShape(1,1));
        
        addImage(new BodyImage("data/Props/Tilesets/UndergroundTile.png")).setScale(2f);
        this.setPosition(position);
        this.addCollisionListener(this);
        
    }

    /**
     *
     * @return
     * Health
     */
    public int getHealth() 
    {
        return health;
    }

    /**
     *
     * @param health
     * Health of the Block
     */
    public void setHealth(int health) 
    {
        this.health = health;
    }

    /**
     * This is an Event. Listens for when the Boulder hits it. After it is hit the
     * blocks health is reduced. If health is less than or equal to 0 then destroy. 
     * 
     * @param e
     * CollisionEvent
     */
    @Override
    public void collide(CollisionEvent e) 
    {
        if((e.getOtherBody().getClass() == Boulder.class)==true)
        {
            setHealth(getHealth() - 100);
            if(getHealth() <= 0){
                this.destroy();
            }
        }
    }
    

}
