/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootergame.weapon;

import city.cs.engine.BoxShape;
import city.cs.engine.Walker;
import city.cs.engine.World;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Bullet objects are shot as projectiles. These can be detected by Enemies which
 * is then used to reduce Enemy health.
 * 
 * Bullets are destroy 500 mili seconds later to save system resources.
 * @author Tahmid
 */
public class Bullet extends Walker{
    
    private Timer timer;
      
    /**
     * Constructor for Bullet.
     * @param world
     * World in which the Bullet spawns in.
     * @param shapeX
     * Width shape of the Bullet.
     * @param shapeY
     * Height Shape of the Bullet.
     */
    public Bullet(World world, float shapeX, float shapeY)
    {
        super(world, new BoxShape(shapeX,shapeY));
        setBullet(true);//set this projectile as a bullet
        
        //Create a timer and destroy this class after a while...
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run() {
                try{
                Bullet.this.destroy();
                }catch(NullPointerException npe){}
            }
        }, 500,500);
    }

}
