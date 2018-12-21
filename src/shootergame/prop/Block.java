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
 * Single Grass Blocks.
 * 
 * @author Tahmid
 */
public class Block extends StaticBody
{
    private BodyImage BIGrass;
    private AttachedImage AIGrass;
    
    private BodyImage BIGround;
    private AttachedImage AIGround;
    
    /**
     * Constructor for the grass block.
     * @param world
     * World where the block will be placed in.
     * @param position
     * Position of the block.
     */
    public Block(World world, Vec2 position)
    {
        super(world, new BoxShape(1,1.35f, new Vec2(0,-0.6f)));
        
        BIGrass = new BodyImage("data/Props/Tilesets/GroundGrass.png");
        AIGrass = new AttachedImage(this, BIGrass,1.6f,0f,new Vec2(0,0));
        
        BIGround = new BodyImage("data/Props/Tilesets/underground.png");
        AIGround = new AttachedImage(this, BIGround,2f,0f,new Vec2(0,-1));
        
        setPosition(position);
    }
    
}
