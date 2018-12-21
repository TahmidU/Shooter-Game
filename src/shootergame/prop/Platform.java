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
 * Block with Rocky texture. Made to be a platform the player can walk on.
 * 
 * @author Tahmid
 */
public class Platform extends StaticBody
{
    private BodyImage BILeftG;
    private AttachedImage AILeftG;
    
    private BodyImage BIMidG;
    private AttachedImage AIMidG;
    
    private BodyImage BIRightG;
    private AttachedImage AIRightG;
    
    /**
     * Constructor for the Platform
     * @param world
     * World in which the platform spawns in.
     */
    public Platform(World world) {
        super(world, new BoxShape(3,3f));
        
        
        
        BILeftG = new BodyImage("data/Props/Tilesets/LeftPlat.png");
        AILeftG = new AttachedImage(this, BILeftG,6f,0f,new Vec2(-2,0));
        
        BIMidG = new BodyImage("data/Props/Tilesets/MidPlat.png");
        AIMidG = new AttachedImage(this, BIMidG,6f,0f,new Vec2(0,0));
        
        BIRightG = new BodyImage("data/Props/Tilesets/RightPlat.png");
        AIRightG = new AttachedImage(this, BIRightG,6f,0f,new Vec2(2,0));

        
    }
    
}
