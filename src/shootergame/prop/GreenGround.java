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
import java.util.concurrent.ThreadLocalRandom;
import org.jbox2d.common.Vec2;

/**
 * Produces the ground blocks. The decoration are randomly generated.
 * 
 * @author Tahmid
 */
public class GreenGround extends StaticBody
{
    private BodyImage BILeftG;
    private AttachedImage AILeftG;
    
    private BodyImage BIRightG;
    private AttachedImage AIRightG;
    
    private BodyImage BIGG;
    private AttachedImage[] AIGGs;
    private int startGGPosX = -127;
    
    private BodyImage BIGR;
    private AttachedImage[] AIGRs;
    private int randomGRPosX = ThreadLocalRandom.current().nextInt(-125,125+1);//Choose a random number
    
    private BodyImage BIGG2;
    private AttachedImage[] AIGG2s;
    private int randomGG2PosX = ThreadLocalRandom.current().nextInt(-127,127+1);//Choose a random number
    
    private BodyImage BIR3;
    private AttachedImage[] AIR3s;
    private int randomR3PosX = ThreadLocalRandom.current().nextInt(-127,127+1);//Choose a random number
    
    private BodyImage BIU;
    private AttachedImage[] AIUs;
    private int startUPosX = -127;
    
    /**
     * Constructor for GreenGround
     * @param world
     * World in which the blocks would spawn in.
     */
    public GreenGround(World world)
    {
        super(world, new BoxShape(130,1));
        
        BILeftG = new BodyImage("data/Props/Tilesets/LeftGround.png");
        AILeftG = new AttachedImage(this, BILeftG,5f,0f,new Vec2(-129,-1));
        
        BIRightG = new BodyImage("data/Props/Tilesets/RightGround.png");
        AIRightG = new AttachedImage(this, BIRightG,5f,0f,new Vec2(129,-1.5f));
        
        BIGG = new BodyImage("data/Props/Tilesets/GroundGrass.png");
        AIGGs = new AttachedImage[128]; //Array that stores all the ladders.
        for(int i = 0; i< AIGGs.length; i++)
        {
            AIGGs[i] = new AttachedImage(this,BIGG,2f,0f,new Vec2(startGGPosX,0)); //Store Ground Grass in Array and contruct them
            startGGPosX += 2;//increment the X for the next Ground Grass
        }
        
        BIGR = new BodyImage("data/Props/Tilesets/GroundRock.png");
        AIGRs = new AttachedImage[10]; //Array that stores all the ladders.
        for(int i = 0; i< AIGRs.length; i++)
        {
            AIGRs[i] = new AttachedImage(this,BIGR,2f,0f,new Vec2(randomGRPosX,0)); //Store Ground Grass in Array and contruct them
            randomGRPosX = ThreadLocalRandom.current().nextInt(-125,125+1);//Choose another random number
        }
        
        BIGG2 = new BodyImage("data/Props/Tilesets/GroundGrass2.png");
        AIGG2s = new AttachedImage[10]; //Array that stores all the ladders.
        for(int i = 0; i< AIGG2s.length; i++)
        {
            AIGG2s[i] = new AttachedImage(this,BIGG2,2f,0f,new Vec2(randomGG2PosX,0.2f)); //Store Ground Grass in Array and contruct them
            randomGG2PosX = ThreadLocalRandom.current().nextInt(-127,127+1);//Choose another random number
        }
        
        BIR3 = new BodyImage("data/Props/Tilesets/Rock3.png");
        AIR3s = new AttachedImage[8]; //Array that stores all the ladders.
        for(int i = 0; i< AIR3s.length; i++)
        {
            AIR3s[i] = new AttachedImage(this,BIR3,1f,0f,new Vec2(randomR3PosX,0.1f)); //Store Ground Grass in Array and contruct them
            randomR3PosX = ThreadLocalRandom.current().nextInt(-127,127+1);//Choose another random number
        }
        
        BIU = new BodyImage("data/Props/Tilesets/Underground5.png");
        AIUs = new AttachedImage[128]; //Array that stores all the ladders.
        for(int i = 0; i< AIUs.length; i++)
        {
            AIUs[i] = new AttachedImage(this,BIU,1.2f,0f,new Vec2(startUPosX,-1f)); //Store Ground Grass in Array and contruct them
            startUPosX += 2;
        }
    }
    
}
