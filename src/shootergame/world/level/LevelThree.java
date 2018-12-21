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
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jbox2d.common.Vec2;
import shootergame.Game;
import shootergame.filemanager.OptionManager;
import shootergame.interactor.Boulder;
import shootergame.interactor.CaveTileDestructible;
import shootergame.interactor.HitEnemy;
import shootergame.prop.GenerateCaveTile;
import shootergame.prop.InvisibleBlock;
import shootergame.world.GameFrame;
import shootergame.world.GameLevel;
import shootergame.world.GameView;

/**
 * Third Level. Parent Class is GameLevel.
 * @author Tahmid
 */
public class LevelThree extends GameLevel
{

    /**
     * Constructor
     */
    public LevelThree(){}
    
    private Game game;
    private GameView view;
    private GameFrame frame;
    
    private Vec2 startPos = new Vec2(-130,-10);
    
    private Timer timer;
    private int goal = 5;
    
    private SoundClip caveSound;
    private SoundClip caveSound2;
    private double masterVolume;
    
    private HitEnemy he;
    private HitEnemy he2;
    private HitEnemy he3;
    private HitEnemy he4;
    private HitEnemy he5;

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
    public void populate(Game game, GameView view, GameFrame frame) throws IOException
    {
        super.populate(game, view, frame);
        
        this.game = game;
        this.view = view;
        this.frame = frame;
        
        
        
        getPlayer().setStartPos(startPos);
        
        setGravity(28f);
        
        //Floor Titles
        GenerateCaveTile generateCave = new GenerateCaveTile(this,-130,-17,130,3);
        InvisibleBlock invisibleBlockFloor = new InvisibleBlock(this, new BoxShape(130, 0.01f));
        invisibleBlockFloor.setPosition(new Vec2(0,-12f));
        
        
        //Invisible Blocks
        InvisibleBlock invisibleBlock = new InvisibleBlock(this, new BoxShape(6f, 6f));
        invisibleBlock.setPosition(new Vec2(-137,-6f));
        InvisibleBlock tinyInvisibleBlock = new InvisibleBlock(this, new BoxShape(1f, 0.5f));
        tinyInvisibleBlock.setPosition(new Vec2(-130,0f));
        
        InvisibleBlock invisibleBlock2 = new InvisibleBlock(this, new BoxShape(6f, 6f));
        invisibleBlock2.setPosition(new Vec2(135,-6f));
        InvisibleBlock tinyInvisibleBlock2 = new InvisibleBlock(this, new BoxShape(1f, 0.5f));
        tinyInvisibleBlock2.setPosition(new Vec2(128,0f));
        
        //Destructible Tiles
        int TempX1 = -128;
        for(int i = 0; i < 7; i++)
        {
            CaveTileDestructible caveTitleDestructible = new CaveTileDestructible(this,new Vec2(TempX1, 0));
            TempX1 += 2;
        }
        
        int TempX2 = -75;
        for(int i = 0; i < 7; i++)
        {
            CaveTileDestructible caveTitleDestructible = new CaveTileDestructible(this,new Vec2(TempX2, 0));
            TempX2 += 2;
        }
        
        int TempX3 = -14;
        for(int i = 0; i < 7; i++)
        {
            CaveTileDestructible caveTitleDestructible = new CaveTileDestructible(this,new Vec2(TempX3, 0));
            TempX3 += 2;
        }
        
        int TempX4 = 50;
        for(int i = 0; i < 7; i++)
        {
            CaveTileDestructible caveTitleDestructible = new CaveTileDestructible(this,new Vec2(TempX4, 0));
            TempX4 += 2;
        }
        
        int TempX5 = 113;
        for(int i = 0; i < 7; i++)
        {
            CaveTileDestructible caveTitleDestructible = new CaveTileDestructible(this,new Vec2(TempX5, 0));
            TempX5 += 2;
        }
        try{
            he = new HitEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
            he.setPosition(new Vec2(-110, -10));
            
            he2 = new HitEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
            he2.setPosition(new Vec2(-90, -10));
            
            he3 = new HitEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
            he3.setPosition(new Vec2(-50, -10));
            
            he4 = new HitEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
            he4.setPosition(new Vec2(10, -10));
            
            he5 = new HitEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
            he5.setPosition(new Vec2(60, -10));
            
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(LevelTwo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(LevelTwo.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Set starting view to player
        view.setCentre(new Vec2(getPlayer().getPlayerXPos(),0));
        openSound();
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
                try{
                    new Boulder(getPlayer().getWorld());
                }catch(NullPointerException npe){}
                if(isCompleted() == true)
                {
                    try {
                        game.goNextLevel();
                        //System.out.println("Tick...");
                    } catch (IOException ex) {
                        Logger.getLogger(LevelOne.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(masterVolume > 0){
                    int randomNum = ThreadLocalRandom.current().nextInt(0, 100+1);
                    if(randomNum<4)
                    {
                        caveSound2.play();
                        
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
     *
     * @return
     * Starting Position for Player.
     */
    @Override
    public Vec2 getStartPos()
    {
        return startPos;
    }
    
    /**
     * Close Sound Files.
     */
    @Override
    public void stopSound()
    {
        super.stopSound();
        if(masterVolume > 0){
            caveSound.close();
            caveSound2.close();
            he.stopSound();
            he2.stopSound();
            he3.stopSound();
            he4.stopSound();
            he5.stopSound();
        }
    }
    
    /**
     * Play Sound Files.
     */
    @Override
    public void playSound()
    {
        if(masterVolume > 0){
            try {
                caveSound.loop();
                he.openSound();
                he2.openSound();
                he3.openSound();
                he4.openSound();
                he5.openSound();
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(LevelThree.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LevelThree.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(LevelThree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Pause Sound Files.
     */
    @Override
    public void pauseSound()
    {
        //super.pauseSound();
        if(masterVolume > 0){
            caveSound.pause();
            caveSound2.pause();
            he.pauseSound();
            he2.pauseSound();
            he3.pauseSound();
            he4.pauseSound();
            he5.pauseSound();
        }
    }
    
    /**
     * Open Sound Files.
     * @throws FileNotFoundException
     * File not found.
     * @throws UnsupportedEncodingException
     * Audio unsupported.
     * @throws IOException
     * IO Error.
     */
    @Override
    public void openSound()
            throws FileNotFoundException, UnsupportedEncodingException, IOException
    {
        //super.playSound();
        try {
            OptionManager optionsManager = new OptionManager();
            optionsManager.read();
            masterVolume = Double.parseDouble(optionsManager.getLine(0).substring(8));
            if(masterVolume > 0)
            {
                caveSound = new SoundClip("data/Sound/Cave/Rocks2.wav");
                caveSound2 = new SoundClip("data/Sound/Cave/Rocks1.wav");
                caveSound.setVolume(masterVolume);
                caveSound2.setVolume(masterVolume);
            }
            he.openSound();
            he2.openSound();
            he3.openSound();
            he4.openSound();
            he5.openSound();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(LevelThree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(LevelThree.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(masterVolume > 0){
            caveSound.loop();
        }
    }
}
