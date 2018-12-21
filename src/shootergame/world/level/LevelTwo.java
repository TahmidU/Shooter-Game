/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.world.level;

import city.cs.engine.BoxShape;
import city.cs.engine.SoundClip;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jbox2d.common.Vec2;
import shootergame.Game;
import shootergame.interactor.HitEnemy;
import shootergame.interactor.ShooterEnemy;
import shootergame.prop.InvisibleBlock;
import shootergame.interactor.Lava;
import shootergame.prop.Platform;
import shootergame.world.GameFrame;
import shootergame.world.GameLevel;
import shootergame.world.GameView;


/**
 * Two Level. Parent Class is GameLevel.
 * 
 * @author Tahmid
 */
public class LevelTwo extends GameLevel
{
    
    //Level switching
    private boolean LevelComplete = false;
    
    private GameView view;
    private Game game;
    private GameFrame frame;
    
    private Lava lava;
    
    private Vec2 startPos  = new Vec2(-130,-6);
    
    private Timer timer;
    private int goal = 5;
    
    private HitEnemy he;
    private HitEnemy he2;
    private HitEnemy he3;
    private ShooterEnemy se;
    private ShooterEnemy se2;
    
    /**
     * Constructor
     */
    public LevelTwo(){}
    
    /**
     * Populates the World with objects, enables controls and plays sound files.
     * @param game
     * Game used to restart and control level progression.
     * @param view
     * GameView used by the frame.
     * @param frame
     * Frame used by the window.
     * @throws IOException
     * IO Error.
     */
    @Override
    public void populate(Game game,GameView view, GameFrame frame) throws IOException
    {
        super.populate(game, view, frame);
        
        
        this.view = view;
        this.game = game;
        this.frame = frame;
        
        
        
        //InvisibleBlock invisibleBlock = new InvisibleBlock(this, new BoxShape(1f,1f));
        
        
        this.view = view;
        this.game = game;
        
        //startPos = new Vec2(-(130*getRelX()),-(6*getRelY()));
        getPlayer().setStartPos(startPos);
        setGravity(28f);
        
        //Platforms
        float TempX1 = -130;
        for(int i = 0; i<6; i++)
        {
            Platform plat = new Platform(this);
            plat.setPosition(new Vec2(TempX1,-11));
            TempX1 += 7;
        }
        
        Platform plat2 = new Platform(this);
        plat2.setPosition(new Vec2(-79, -9));
        
        Platform plat3 = new Platform(this);
        plat3.setPosition(new Vec2(-70, -6));
        
        float TempX2 = -61;
        for(int i = 0; i<9; i++)
        {
            Platform plat4 = new Platform(this);
            plat4.setPosition(new Vec2(TempX2, -4));
            TempX2 += 7;
        }
        
        Platform plat5 = new Platform(this);
        plat5.setPosition(new Vec2(4, -2));
        Platform plat6 = new Platform(this);
        plat6.setPosition(new Vec2(11,-2));
        
        Platform plat7 = new Platform(this);
        plat7.setPosition(new Vec2(19, -4));
        Platform plat8 = new Platform(this);
        plat8.setPosition(new Vec2(26, -4));
        
        Platform plat9 = new Platform(this);
        plat9.setPosition(new Vec2(34, -2));
        Platform plat10 = new Platform(this);
        plat10.setPosition(new Vec2(41,-2));
        
        float TempX3 = 51;
        for(int i = 0; i<6; i++)
        {
            Platform plat11 = new Platform(this);
            plat11.setPosition(new Vec2(TempX3, -6));
            TempX3 += 7;
        }
        
        float TempX4 = 99;
        for(int i = 0; i<5; i++)
        {
            Platform plat12 = new Platform(this);
            plat12.setPosition(new Vec2(TempX4, -5));
            TempX4 += 7;
        }
        
        InvisibleBlock invisibleBlock = new InvisibleBlock(this,new BoxShape(6f,6f));
        invisibleBlock.setPosition(new Vec2(-140,-6));
        InvisibleBlock tinyInvisibleBlock = new InvisibleBlock(this, new BoxShape(2f,0.5f));
        tinyInvisibleBlock.setPosition(new Vec2(new Vec2(-134,0)));
        
        InvisibleBlock invisibleBlock2 = new InvisibleBlock(this,new BoxShape(6f,6f));
        invisibleBlock2.setPosition(new Vec2(137,-2));
        InvisibleBlock tinyInvisibleBlock2 = new InvisibleBlock(this, new BoxShape(2f,0.5f));
        tinyInvisibleBlock2.setPosition(new Vec2(new Vec2(129,4)));
        
        
        
        try {
            he = new HitEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
            he.setPosition(new Vec2(-110,-5));
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(LevelTwo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(LevelTwo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        se = new ShooterEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
        se.setPosition(new Vec2(-40, -1));
        
        try
        {
            he2 = new HitEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
            he2.setPosition(new Vec2(60, 5));
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(LevelTwo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(LevelTwo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        se2 = new ShooterEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
        se2.setPosition(new Vec2(25, 10));
        
        try{
            he3 = new HitEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
            he3.setPosition(new Vec2(110, 5));
        }catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(LevelTwo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(LevelTwo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        lava = new Lava(this, getPlayer(), new Vec2(0,-15),130, -130);
        openSound();
        
        //Set starting view to player
        view.setCentre(new Vec2(getPlayer().getPlayerXPos(),0));
        
    }
    
    /**
     * Update and schedule tasks using timers.
     */
    @Override
    public void tick()
    {
        super.tick();
        timer = new Timer();
        
        timer.schedule(new TimerTask(){
            
            @Override
            public void run()
            {
                
                if(isCompleted() == true)
                {
                    try {
                        System.out.println("check");
                        game.goNextLevel();
                        //System.out.println("Tick...");
                    } catch (IOException ex) {
                        Logger.getLogger(LevelOne.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }, 1000,1000);
    }
    
    /**
     * Check if the player has met the goal. If the player did meet the goal then
     * move to next level.
     * @return
     * Either true or false.
     */
    public boolean isCompleted()
    {
        if(getPlayer().getEnemiesKilled() == goal)
        {
            timer.cancel();
            getPlayer().setEnemiesKilled(0);
            return true;
        }
        return false;
    }
    
    /**
     * @return
     * Starting Position for Player.
     */
    @Override
    public Vec2 getStartPos()
    {
        return startPos;
    }
    
    /**
     * Close Sound File.
     */
    @Override
    public void stopSound()
    {
        try
        {
            lava.stopSound();
            he.stopSound();
            he2.stopSound();
            he3.stopSound();
            se.stopSound();
            se2.stopSound();
        }catch(NullPointerException npe){}
    }
    
    /**
     * Pause Sound File.
     * @throws NullPointerException
     * If lava is null.
     */
    @Override
    public void pauseSound() throws NullPointerException
    {
        lava.pauseSound();
    }
    
    /**
     * Play Sound File.
     */
    @Override
    public void playSound()
    {
        lava.playSound();
    }
    
    /**
     * Open Sound File.
     * @throws IOException
     * IO Error.
     */
    @Override
    public void openSound() throws IOException
    {
        try {
            lava.openSound();
            he.openSound();
            he2.openSound();
            he3.openSound();
            se.openSound();
            se2.openSound();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(LevelTwo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(LevelTwo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
