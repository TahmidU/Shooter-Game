/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.prop;

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * Huge blocks intended to prevent the player from escaping the map.
 * 
 * @author Tahmid
 */
public class Blocker extends StaticBody
{
    private BodyImage BIMid;
    private AttachedImage AIMid;
    
    /**
     * Constructor for Block
     * @param world
     * World in which the Block will be placed in.
     * @param shape
     * Shape of the block. Defines the objects perimeters for the collisions. 
     */
    public Blocker(World world, BoxShape shape)
    {
        super(world, shape);
        
        BIMid = new BodyImage("data/Props/Tilesets/underground.png");
        AIMid = new AttachedImage(this, BIMid,12f,0f,new Vec2(0,0));
        
    }
    
}
