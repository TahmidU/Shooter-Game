/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootergame.weapon;

import city.cs.engine.World;

/**
 * EnemyBullet objects are shot as projectiles. These can be detected by the Player which
 * is then used to reduce Player health.
 * 
 * Bullets are destroy 500 mili seconds later to save system resources.
 * @author Tahmid
 */
public class EnemyBullet extends Bullet
{
    
    /**
     * Constructor for EnemyBullet.
     * @param world
     * World in which the EnemyBullet spawns in.
     * @param shapeX
     * Width shape of the EnemyBullet.
     * @param shapeY
     * Height Shape of the EnemyBullet.
     */
    public EnemyBullet(World world, float shapeX, float shapeY) 
    {
        super(world, shapeX, shapeY);
    }
    
}
