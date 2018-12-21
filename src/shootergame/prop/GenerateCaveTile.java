/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.prop;

import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * Generates Arrays of blocks. This is used to make 2 dimensional terrains. The 
 * terrain generator will auto texture the terrain. With this you can set the 
 * length/height and width of the terrain.
 * 
 * @author Tahmid
 */
public class GenerateCaveTile extends StaticBody
{
    
    private int startPosX;
    private int posX;
    private int posY;
    
    private CaveTile[][] blocks;
    
    private int width;
    private int height;
    
    /**
     * Constructor for GenerateCaveTitle
     * 
     * @param world
     * World in which the blocks would spawn in.
     * @param posX
     * starting x position of the first block.
     * @param posY
     * starting y position of the first block.
     * @param width
     * Width of the terrain (amount of blocks on x-axis).
     * @param height
     * Height of the terrain (amount of blocks on y-axis).
     */
    public GenerateCaveTile(World world, int posX, int posY, int width, int height)
    {
        super(world);
        
        
        this.startPosX = posX;
        this.posX = posX;
        this.posY = posY;
        
        this.width = width;
        this.height = height;
        
        blocks = new CaveTile[this.height][this.width];
        
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                blocks[i][j] = new CaveTile(world, new Vec2(this.posX, this.posY));
                this.posX += 2f;
            }
            this.posX = startPosX;
            this.posY += 2f;
        }
    }
    
}
