/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/


package shootergame.weapon;

import city.cs.engine.SoundClip;
import shootergame.interactor.Player;
import city.cs.engine.StaticBody;
import city.cs.engine.UserView;
import city.cs.engine.World;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.jbox2d.common.Vec2;
import shootergame.filemanager.OptionManager;

/**
 * This is the Weapons class. It determines the fire rate, ammo, clip capacity, amount
 * of clips, sets damage and reloads the weapons.
 * 
 * This is also the class responsible for shooting projectiles.
 * 
 * 
 * @author Tahmid
 *
 */
public class Weapon extends StaticBody{
    
    //Weapon properties
    private int fireRate; //1 bullet per x milliseconds
    private int ammo; //current ammo
    private int ammoLimit; //Restricted ammo per clip
    private int clip;
    private int damage;
    
    //Weapon controls
    private InputMap im;
    private ActionMap am;
    
    //Main view and world
    private UserView view;
    private World world;
    
    //Bullet
    private Vec2 speed = new Vec2(160,10);
    private Bullet bulletWalker;
    private long lastPressed;
    
    //Player
    private Player player;
    
    //Sounds
    private SoundClip shootSound;
    private String soundDirectory;
    private double masterVolume;
    
    /**
     * Constructor for Weapon
     * @param world
     * World in which the weapon would spawn in.
     * @param view
     * View used by the input and action maps.
     * @param player
     * Tracks position of the player.
     * @param fireRate
     * Fire rate of the weapon
     * @param ammoLimit
     * Clip Capacity of the clip.
     * @param damage
     * Damage that each bullet does.
     * @param ammo
     * Ammo of the weapon.
     * @param clip
     * Number of clips.
     * @param soundDirectory
     * Location of gun shot Sound file.
     */
    public Weapon(World world, UserView view, Player player, int fireRate, int ammoLimit, int damage
            ,int ammo, int clip, String soundDirectory)
    {
        super(world);
        this.view = view;
        this.world = world;
        this.player = player;
        this.fireRate = fireRate;
        this.ammoLimit = ammoLimit;
        this.damage = damage;
        this.ammo = ammo;
        this.clip = clip;
        this.soundDirectory = soundDirectory;
        try {
            OptionManager optionsManager = new OptionManager();
            optionsManager.read();
            masterVolume = Double.parseDouble(optionsManager.getLine(0).substring(8));
            if(masterVolume > 0)
            {
            shootSound = new SoundClip(soundDirectory);
            shootSound.setVolume(masterVolume);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException| IllegalArgumentException ex) {
            Logger.getLogger(Weapon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the bullets spawning, position and speed.
     */
    public void shoot()
    {
        //Create projectiles
        bulletWalker = new Bullet(world, 0.1f, 0.1f);
        
        if(player.getCurrentChar() == 'd')
        {
            //if the last key pressed is d then get the player position, set speed and spawn the bullet
            bulletWalker.setPosition(player.getPosition());
            bulletWalker.setLinearVelocity(speed);
            
        }
        if(player.getCurrentChar() == 'a')
        {
            
            //if the last key pressed is a then get the player position, set speed and spawn the bullet
            Vec2 turnedPos = new Vec2(player.getPosition().x-0.5f,player.getPosition().y);//this is to prevent the projectiles from colliding on the player
            bulletWalker.setPosition(turnedPos);
            bulletWalker.setLinearVelocity(new Vec2(-speed.x,speed.y));//set velocity on opposite direction
        }
        
    }

    /**
     * Enables the controls for shooting.
     */
    public void keyBinding()
    {
        lastPressed = 0;

        
        im = this.view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        am = this.view.getActionMap();
        
        //When Keys are pressed...
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0 , false), "EnterPressed");
        
        //When Key released...
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0, true), "EnterReleased");
        
        am.put("EnterPressed", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                if(ammo != 0)
                {
                    //Handles the fireRate of the weapon
                    if((System.currentTimeMillis() - lastPressed) > fireRate)
                    {
                        if(masterVolume > 0)
                        {
                            shootSound.play();
                        }
                        shoot();
                        ammo -= 1;
                        //System.out.println(ammo);
                        lastPressed = System.currentTimeMillis();
                        
                        
                        System.out.println(soundDirectory);
                        
                    }
                    
                    
                }
                
                if(ammo == 0 && clip > 0)
                {
                    clip -= 1;
                    //System.out.println("Reload");
                    ammo =+ ammoLimit;
                }
                
            }
        });
        
        
        if(masterVolume > 0)
        {
            shootSound.stop();
        }
    }
    
    /**
     *
     * @return
     * FireRate
     */
    public int getFireRate() {
        return fireRate;
    }
    
    /**
     *
     * @param fireRate
     * Set fireRate.
     */
    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }
    
    /**
     *
     * @return
     * Ammo
     */
    public int getAmmo() {
        return ammo;
    }
    
    /**
     *
     * @param ammo
     * Set ammo.
     */
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
    
    /**
     *
     * @return
     * Clip capacity.
     */
    public int getAmmoLimit() {
        return ammoLimit;
    }
    
    /**
     *
     * @param ammoLimit
     * Set clip capacity.
     */
    public void setAmmoLimit(int ammoLimit) {
        this.ammoLimit = ammoLimit;
    }
    
    /**
     *
     * @return
     * Clip
     */
    public int getClip() {
        return clip;
    }
    
    /**
     *
     * @param clip
     * Set clip amount.
     */
    public void setClip(int clip) {
        this.clip = clip;
    }
    
    /**
     *
     * @return
     * Damage
     */
    public int getDamage() {
        return damage;
    }
    
    /**
     *
     * @param damage
     * Set Damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    /**
     *
     * @return
     * Shoot Sound file.
     */
    public SoundClip getShootSound() {
        return shootSound;
    }
    
    /**
     *
     * @return
     * Directory of the shoot sound file.
     */
    public String getSoundDirectory() {
        return soundDirectory;
    }
    
}
