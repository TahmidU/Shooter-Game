/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootergame.interactor;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * This creates an array of ladder. 
 * 
 * @author Tahmid
 */
public class GenerateLadder 
{
    
    private float posX;
    private float startPosY;
    private Ladder[] ladders;

    /**
     *
     * @param world
     * World the ladder will be in.
     * @param player
     * Player object the ladder will interact with.
     * @param posX
     * current x-axis position
     * @param startPosY
     * start x-axis position
     * @param length
     * Amount of ladders (y-axis bottom to top).
     */
    public GenerateLadder(World world, Player player, float posX, float startPosY, int length) 
    {
        this.posX = posX; //X position of ladders
        this.startPosY = startPosY; //starting Y position of ladders
        ladders = new Ladder[length]; //Array that stores all the ladders.
        
        for(int i = 0; i< ladders.length; i++)
        {
            ladders[i] = new Ladder(world, player, new Vec2(posX,startPosY)); //Store ladders in Array and contruct them
            startPosY += 0.5f;//increment the Y for the next ladder
        }
    }

    /**
     *
     * @return
     * x-axis position of the ladder.
     */
    public float getPosX() {
        return posX;
    }

    /**
     *
     * @param posX
     * set the x-axis position of the ladder.
     */
    public void setPosX(float posX) {
        this.posX = posX;
    }

    /**
     *
     * @return
     * starting x-axis position.
     */
    public float getStartPosY() {
        return startPosY;
    }

    /**
     *
     * @param startPosY
     * starting y-axis position.
     */
    public void setStartPosY(float startPosY) {
        this.startPosY = startPosY;
    }

    /**
     *
     * @return
     * Ladder stored in array
     */
    public Ladder[] getLadders() {
        return ladders;
    }

    /**
     *
     * @param ladders
     * set array of ladders.
     */
    public void setLadders(Ladder[] ladders) {
        this.ladders = ladders;
    }
    
    
    
}
