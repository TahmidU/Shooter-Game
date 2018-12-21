/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootergame.interactor;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.World;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import org.jbox2d.common.Vec2;

/**
 * Creates the Boulder Prop. Damages the player and randomly spawns.
 * 
 * @author Tahmid
 */
public class Boulder extends DynamicBody
{
    
    private Timer timer;
    
    /**
     * Constructor for the Boulder.  
     * 
     * @param world
     * World the Bolder is placed in.
     * @throws NullPointerException
     * If Boulder is null.
     */
    public Boulder(World world) throws NullPointerException
    {
        super(world, new BoxShape(2,2));
        addImage(new BodyImage("data/Props/Tilesets/Rock2.png")).setScale(3f);
        int randomNum = ThreadLocalRandom.current().nextInt(-120, 120+1);
        this.setPosition(new Vec2(randomNum, 10));
        
        
        //Create a timer and destroy this class after a while...
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run() {
                try{
                timer.cancel();
                Boulder.this.destroy();
                }catch(NullPointerException npe){}
            }
        }, 2000,2000);
    }
    
}
