/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootergame.prop;

import city.cs.engine.BodyImage;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

/**
 * Invisible blocks (made with transparent images) that the user can see. 
 * Made to prevent user from escaping the map.
 * 
 * @author Tahmid
 */
public class InvisibleBlock extends StaticBody 
{
    
    /**
     * Constructor for the InvisibleBlock.
     * @param world
     * World in which the block spawns in.
     * @param shape
     * Shape of the blocks. Defines the objects perimeters for collision.
     */
    public InvisibleBlock(World world, Shape shape) 
    {
        super(world, shape);
        addImage(new BodyImage("data/Props/Transparent.png"));
    }
    
}
