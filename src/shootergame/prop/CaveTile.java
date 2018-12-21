/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootergame.prop;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * Single CaveTile Block. Used in Terrain Generators.
 * 
 * @author Tahmid
 */
public class CaveTile extends StaticBody
{
    
    /**
     * Constructor for CaveTile.
     * @param world
     * World in which the block will be in.
     * @param position
     * Position of the block.
     */
    public CaveTile(World world, Vec2 position) 
    {
        super(world, new BoxShape(1,1));
        
        addImage(new BodyImage("data/Props/Tilesets/UndergroundTile.png")).setScale(2f);
        this.setPosition(position);
    }
    
}
