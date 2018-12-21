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
 * Blocks that have the underground texture on. These are used in terrain generators.
 * 
 * @author Tahmid
 */
public class BlockUnderground extends StaticBody
{
    
    private BodyImage BIGround;
    private AttachedImage AIGround;
    
    /**
     * Constructor for BlockUnderground
     * @param world
     * World in which the Block will be placed in.
     * @param position
     * position of the block.
     */
    public BlockUnderground(World world, Vec2 position) 
    {
        super(world, new BoxShape(1,0.5f));
        
        BIGround = new BodyImage("data/Props/Tilesets/underground5.png");
        AIGround = new AttachedImage(this, BIGround,1f,0f,new Vec2(0,0));
        
        setPosition(position);
    }
    
}
