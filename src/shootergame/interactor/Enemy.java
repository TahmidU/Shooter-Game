/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.interactor;

import city.cs.engine.BodyImage;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.PolygonShape;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import city.cs.engine.Shape;
import city.cs.engine.Walker;
import city.cs.engine.World;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
import shootergame.weapon.Bullet;
import shootergame.weapon.gun.MachineGun;
import shootergame.weapon.gun.Pistol;
import shootergame.weapon.gun.Shotgun;

/**
 * Enemy class. Parent of HitEnemy and ShooterEnemy. This class is only able to detect
 * the player right after the sensor is added.
 * 
 * @author Tahmid
 */
public class Enemy extends Walker implements CollisionListener, SensorListener
{
    
    //Enemy Properties
    private static final Shape enemyShape = new PolygonShape(-0.68f,1.47f, 0.69f
            ,1.48f, 0.95f,0.61f, 0.91f,-1.42f, -0.9f,-1.41f, -0.93f,0.61f);
    private int health;
    private static int damage;
    
    //Sensor attribute
    private float xDirection;
    private String direction;
    private BodyImage right;
    private BodyImage left;
    
    //Player info
    private Player player;
    private MachineGun mg;
    private Pistol p;
    private Shotgun s;
    
    /**
     * Constructor for the enemy class.
     * 
     * @param world
     * World the Enemy will be in.
     * @param player
     * Player the Enemy will detect.
     * @param mg
     * MG weapon
     * @param p
     * Pistol Weapon
     * @param s
     * Shotgun Weapon
     * @param health
     * Enemy Health
     * @param damage
     * Enemy damage
     * @param rightDirectory
     * image directory that loads when the enemy detects the player on its right.
     * @param leftDirectory
     * image directory that loads when the enemy detects the player on its left.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Enemy(World world, Player player, MachineGun mg, Pistol p, Shotgun s, int health, int damage
            , String rightDirectory, String leftDirectory)
    {
        super(world, enemyShape);
        
        //Player info
        this.player = player;
        this.mg = mg;
        this.p = p;
        this.s = s;
        
        //Enemy properties
        this.health = health;
        this.damage = damage;
        
        right = new BodyImage(rightDirectory,3f);
        left = new BodyImage(leftDirectory,3f);
        addImage(right);
        
        //detect bullet collisions to TAKE damage
        //this.addCollisionListener(new BulletEnemyCollision(player,mg,p,s,this));
        this.addCollisionListener(this);
        
    }
    
    /**
     *
     * @return
     * Enemy Health
     */
    public int getHealth() {
        return health;
    }
    
    /**
     *
     * @param health
     * set Enemy Health
     */
    public void setHealth(int health) {
        this.health = health;
    }
    
    /**
     * This class is static.
     * 
     * @return
     * Enemy Shape
     */
    public static Shape getEnemyShape() {
        return enemyShape;
    }
    
    /**
     *
     * @return
     * Enemy Damage
     */
    public static int getDamage()
    {
        return damage;
    }
    
    /**
     * Listens for when a Bullet hits the shape of the Enemy. Then checks for the
     * weapon the player currently hold, finally takes away an amount of health depending on the 
     * weapon the player is holding currently. If Health equals or less than 0 then the Enemy gets
     * destroyed.
     * 
     * @param e
     * CollisionEvent
     */
    @Override
    public void collide(CollisionEvent e)
    {
        if((e.getOtherBody().getClass() == Bullet.class) == true)
        {
            
            //System.out.println(e.getOtherBody().getClass() == Bullet.class);
            
            
            switch(player.getGun())
            {
                case "mg":
                    this.setHealth(this.getHealth() - mg.getDamage());
                    break;
                case "p":
                    this.setHealth(this.getHealth() - p.getDamage());
                    break;
                case "s":
                    this.setHealth(this.getHealth() - s.getDamage());
                    break;
                    
            }
            
            //System.out.println(enemy.getHealth());
        }
        
        if(this.getHealth() <= 0)
        {
            player.incrementEnemiesKilled();
            this.removeAllCollisionListeners();
            this.destroy();
            try {
                this.finalize();
            } catch (Throwable ex) {
                Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Creates the health pickup
            HealthBox pickHealth = new HealthBox(player.getWorld(), player, "data/PickUp/Health.png", this.getPosition());
            
        }
    }
    
    /**
     * Sensors that determine the direction the enemy will be facing. The direction is
     * determined via the position of the player and enemy.
     * 
     * @param e
     * SensorEvent
     */
    @Override
    public void beginContact(SensorEvent e)
    {
        if(e.getContactBody() == player)
        {
            //System.out.println("Player: " + player.getPosition() + ", Enemy: " + this.getPosition());
            xDirection = player.getPosition().x - this.getPosition().x;
            System.out.println(xDirection);
            if(xDirection > 0)
            {
                setImage(right);
                setDirection("right");
                System.out.println(xDirection);
                
            }else if(xDirection < 0)
            {
                setImage(left);
                setDirection("left");
                System.out.println(xDirection);
            }
        }
        
    }
    
    /**
     * When the player leaves the sensor.
     * 
     * @param e
     * SensorEvent
     */
    @Override
    public void endContact(SensorEvent e){}
    
    /**
     * Direction at which the enemy is currently looking.
     * 
     * @param direction
     * set Direction
     */
    public void setDirection(String direction)
    {
        this.direction = direction;
    }
    
    /**
     * Direction at which the enemy is currently looking.
     * 
     * @return
     * Direction
     */
    public String getDirection()
    {
        return this.direction;
    }
    
    /**
     * Set image applied to the enemy.
     * 
     * @param image
     * set BodyImage
     */
    public void setImage(BodyImage image)
    {
        this.removeAllImages();
        this.addImage(image);
    }
    
    /**
     * Works the difference using the x-axis between the position of the 
     * player - position of the enemy. 
     * 
     * @return
     * xDirection - x-axis only.
     */
    public float getxDirection()
    {
        return xDirection;
    }
    
    /**
     *
     * @return
     * Enemy looking right BodyImage.
     */
    public BodyImage getRight()
    {
        return right;
    }
    
    /**
     *
     * @return
     * Enemy looking right BodyImage.
     */
    public BodyImage getLeft()
    {
        return left;
    }

    
}
