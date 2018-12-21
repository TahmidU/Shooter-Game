/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.prop;

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * Block with rock textures. An array of them can be produced using this class.
 * 
 * @author Tahmid
 */
public class RockyBlocker extends StaticBody
{
    //Gives the centre of the body a rock image
    private BodyImage BIMid;
    private AttachedImage AIMid;
    
    //These render the rock textures on the sides
    private BodyImage BIRight;
    private AttachedImage[] AIRights;
    private int startRPosY = -17;
    
    private BodyImage BILeft;
    private AttachedImage[] AILefts;
    private int startLPosY = -17;
    
    //These render the rock texture on the top right/left
    private BodyImage BIRightT;
    private AttachedImage AIRightT;
    
    private BodyImage BILeftT;
    private AttachedImage AILeftT;
    
    private BodyImage BIGrass;
    private AttachedImage[] AIGrasses;
    private int startGPosX;
    
    /**
     * Constructor for RockyBlocker.
     * @param world
     * World in which the blocks spawn in.
     * @param shape
     * Shape of the block. Shape determines where collisions happen.
     * @param side
     * Which side? (left or right). Correct Image will be loaded depending of this parameter.
     * @param length
     * Length of the blocks (amount on the y-axis).
     */
    public RockyBlocker(World world, Shape shape, String side, int length)
    {
        super(world, shape);
        
        BIMid = new BodyImage("data/Props/Tilesets/underground.png");
        AIMid = new AttachedImage(this, BIMid,12f,0f,new Vec2(0,0));
        
        if(side == "right")
        {
            startGPosX = 5;
            
            BIRight = new BodyImage("data/Props/Tilesets/RightUnderground.png");
            AIRights = new AttachedImage[length]; //Array that stores all the ladders.
            for(int i = 0; i< AIRights.length; i++)
            {
                AIRights[i] = new AttachedImage(this,BIRight,2f,0f,new Vec2(6,startRPosY)); //Store Ground Grass in Array and contruct them
                startRPosY += 2;//increment the X for the next Ground Grass
            }
            BIRightT = new BodyImage("data/Props/Tilesets/RightUnderground2.png");
            AIRightT = new AttachedImage(this, BIRightT,2f,0f,new Vec2(5,5f));
            
            BIGrass = new BodyImage("data/Props/Tilesets/GroundGrass2.png");
            AIGrasses = new AttachedImage[32]; //Array that stores all the ladders.
            for(int i = 0; i< AIGrasses.length; i++)
            {
                AIGrasses[i] = new AttachedImage(this,BIGrass,2f,0f,new Vec2(startGPosX,5.5f)); //Store Ground Grass in Array and contruct them
                startGPosX -= 1;//increment the X for the next Ground Grass
            }
        }
        
        if(side == "left")
        {
            startGPosX = -5;
            
            BILeft = new BodyImage("data/Props/Tilesets/LeftUnderground.png");
            AILefts = new AttachedImage[length]; //Array that stores all the ladders.
            for(int i = 0; i< AILefts.length; i++)
            {
                AILefts[i] = new AttachedImage(this,BILeft,2f,0f,new Vec2(-6,startLPosY)); //Store Ground Grass in Array and contruct them
                startLPosY += 2;//increment the X for the next Ground Grass
            }
            BILeftT = new BodyImage("data/Props/Tilesets/LeftUnderground2.png");
            AILeftT = new AttachedImage(this, BILeftT,2f,0f,new Vec2(-5,5f));
            
            BIGrass = new BodyImage("data/Props/Tilesets/GroundGrass2.png");
            AIGrasses = new AttachedImage[32]; //Array that stores all the ladders.
            for(int i = 0; i< AIGrasses.length; i++)
            {
                AIGrasses[i] = new AttachedImage(this,BIGrass,2f,0f,new Vec2(startGPosX,5.5f)); //Store Ground Grass in Array and contruct them
                startGPosX += 1;//increment the X for the next Ground Grass
            }
        }
        
        
        
    }
    
}
