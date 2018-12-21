/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.prop;

import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;



/**
 * Generates Arrays of blocks. This is used to make 2 dimensional terrains. The 
 * terrain generator will auto texture the terrain. With this you can set the 
 * length/height and width of the terrain.
 * 
 * @author Tahmid
 */
public class GenerateBlock
{
    
    private float BPosX;
    private float BPosY;
    
    private float UPosX;
    private float UPosY;
    
    private float startPosX;
    
    private Block[] blocks;
    private BlockUnderground[][] blockUs;
    
    private int width;
    private int length;
    
    private InvisibleBlock leftInvisibleBlock;
    private InvisibleBlock rightInvisibleBlock;
    private Shape invisibleShape = new BoxShape(0.5f, 0.25f);
    
    /**
     * Constructor for GenerateBlock.
     * @param world
     * World in which the blocks would spawn in.
     * @param PosX
     * Starting x position of the blocks of the first block.
     * @param PosY
     * Starting y position of the blocks of the first block.
     * @param width
     * Width of the terrain (amount of blocks x-axis).
     * @param length
     * Length of the terrain (amount of blocks y-axis).
     */
    public GenerateBlock(World world, float PosX, float PosY,
            int width, int length)
    {
        this.BPosX = PosX;
        this.BPosY = PosY;
        
        this.UPosX = PosX;
        this.UPosY = PosY-2f;
        
        this.startPosX = PosX;
        
        this.width = width;
        this.length = length;
        
        leftInvisibleBlock = new InvisibleBlock(world, invisibleShape);
        leftInvisibleBlock.setPosition(new Vec2(PosX-1f, PosY+0.5f));
        
        rightInvisibleBlock = new InvisibleBlock(world, invisibleShape);
        rightInvisibleBlock.setPosition(new Vec2((PosX+(width*2))-1f, PosY+0.5f));
        
        blocks = new Block[width]; //Array that stores all the blocks
        blockUs = new BlockUnderground[length][width];
        
        for(int i = 0; i< blocks.length; i++)
        {
            blocks[i] = new Block(world, new Vec2(BPosX, BPosY)); //Store Blocks in Array and contruct them
            BPosX += 2f;//increment the X for the next Blocks
        }
        
        for(int i = 0; i < length; i++)
        {
            
            for(int j = 0; j < width; j++)
            {
                blockUs[i][j] = new BlockUnderground(world, new Vec2(UPosX, UPosY));
                UPosX += 2f;
            }
            UPosX = startPosX;
            UPosY -= 1f;
        }
    }
    
    /**
     *
     * @return
     * width
     */
    public int getWidth()
    {
        return width;
    }
    
    /**
     *
     * @param width
     * set width.
     */
    public void setWidth(int width)
    {
        this.width = width;
    }
    
    /**
     *
     * @return
     * length
     */
    public int getLength()
    {
        return length;
    }
    
    /**
     *
     * @param length
     * set length.
     */
    public void setLength(int length)
    {
        this.length = length;
    }
    
    
    
}
