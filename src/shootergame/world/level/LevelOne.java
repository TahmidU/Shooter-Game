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
import shootergame.interactor.GenerateLadder;
import shootergame.interactor.ShooterEnemy;
import shootergame.interactor.Spike;
import shootergame.prop.Blocker;
import shootergame.prop.GenerateBlock;
import shootergame.prop.GreenGround;
import shootergame.prop.InvisibleBlock;
import shootergame.prop.Platform;
import shootergame.prop.RockyBlocker;
import shootergame.world.GameFrame;
import shootergame.world.GameLevel;
import shootergame.world.GameView;

/**
 * First Level. Parent Class is GameLevel.
 * 
 * @author Tahmid
 */
public class LevelOne extends GameLevel{
    

    private boolean LevelComplete = false;
    
    private GameView view;
    private Game game;
    private GameFrame frame;
    
    private Vec2 startPos = new Vec2(-128,-15);
    
    //Objective
    private Timer timer;
    private int goal = 5;
    
    //Enemies
    private SoundClip jungleSound;
    private SoundClip birdSound;
    private double masterVolume;
    
    private ShooterEnemy se;
    private ShooterEnemy se2;
    private ShooterEnemy se3;
    private ShooterEnemy se4;
    private ShooterEnemy se5;
    
    private Spike spike;
    private Spike spike2;
    private Spike spike3;
    private Spike spike4;
    private Spike spike5;
    private Spike spike6;
    private Spike spike7;
    private Spike spike8;
    
    /**
     * Constructor
     */
    public LevelOne(){}
    
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
        
        this.game = game;
        this.view = view;
        this.frame = frame;
        
        try {
            OptionManager optionsManager = new OptionManager();
            optionsManager.read();
            masterVolume = Double.parseDouble(optionsManager.getLine(0).substring(8));
            if(masterVolume > 0){
            jungleSound = new SoundClip("data/Sound/Jungle/jungle.wav");
            birdSound = new SoundClip("data/Sound/Jungle/bird.wav");
            jungleSound.setVolume(masterVolume);
            birdSound.setVolume(masterVolume);
            }
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(LevelOne.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(LevelOne.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(masterVolume > 0){
        jungleSound.loop();
        }
        
        getPlayer().setStartPos(startPos);
        
        se = new ShooterEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
        se.setPosition(new Vec2(-110, -10));
        
        se2 = new ShooterEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
        se2.setPosition(new Vec2(-100, -10));
        
        se3 = new ShooterEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
        se3.setPosition(new Vec2(-50, -10));
        
        se4 = new ShooterEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
        se4.setPosition(new Vec2(10, -10));
        
        se5 = new ShooterEnemy(this,getPlayer(),getMachineG(),getPistol(),getShotgun());
        se5.setPosition(new Vec2(60, -10));
        
        setGravity(28f);
        
        //Ground
        GreenGround gg = new GreenGround(this);
        gg.setPosition(new Vec2(0,-17));
        
        //Blockers - Left
        RockyBlocker LB = new RockyBlocker(this, new BoxShape(6,6),"right",12);
        LB.setPosition(new Vec2(-136,-12));
        
        Blocker blockerL = new Blocker(this, new BoxShape(6,6));
        blockerL.setPosition(new Vec2(-148,-12));
        
        Blocker blockerL2 = new Blocker(this, new BoxShape(6,6));
        blockerL2.setPosition(new Vec2(-160,-12));
        
        InvisibleBlock LIB = new InvisibleBlock(this, new BoxShape(3,1));
        LIB.setPosition(new Vec2(-128,-6));
        
        //Blockers - Right
        RockyBlocker RB = new RockyBlocker(this, new BoxShape(6,6),"left",12);
        RB.setPosition(new Vec2(136,-12));
        
        Blocker blockerR = new Blocker(this, new BoxShape(6,6));
        blockerR.setPosition(new Vec2(148,-12));
        
        Blocker blockerR2 = new Blocker(this, new BoxShape(6,6));
        blockerR2.setPosition(new Vec2(160,-12));
        
        InvisibleBlock RIB = new InvisibleBlock(this, new BoxShape(3,1));
        RIB.setPosition(new Vec2(128,-6));
        
        //Platforms
        Platform plat = new Platform(this);
        plat.setPosition(new Vec2(-22, -9));
        
        //Terrain
        GenerateBlock gb = new GenerateBlock(this,-115,-15,20,0);
        GenerateBlock gb2 = new GenerateBlock(this,-70,-13,20,2);
        GenerateBlock gb3 = new GenerateBlock(this, -10, -12, 5, 3);
        GenerateBlock gb4 = new GenerateBlock(this, 0, -13, 10, 2);
        GenerateBlock gb5 = new GenerateBlock(this, 20, -14, 7, 1);
        GenerateBlock gb6 = new GenerateBlock(this, 43, -11, 8, 4);
        GenerateBlock gb7 = new GenerateBlock(this, 71, -13, 6, 2);
        GenerateBlock gb8 = new GenerateBlock(this, 83, -14, 7, 1);
        
        //Create Pickups here...
        
        //Creates the health pickup 
        //pickHealth = new HealthBox(this, player, "data/PickUp/Health.png", new Vec2(8,-15));
        
        //Create ladders here...
        GenerateLadder ladderStack1 = new GenerateLadder(this,getPlayer(), -26f,-16f,18);
        GenerateLadder ladderStack2 = new GenerateLadder(this, getPlayer(), 59f,-16f, 12);
        
        //Create spikes here...
        spike = new Spike(this, getPlayer(), new Vec2(-74,-15));
        spike2 = new Spike(this, getPlayer(), new Vec2(-72,-15));
        spike3 = new Spike(this, getPlayer(), new Vec2(-14,-15));
        spike4 = new Spike(this, getPlayer(), new Vec2(-12,-15));
        spike5 = new Spike(this, getPlayer(), new Vec2(35,-15));
        spike6 = new Spike(this, getPlayer(), new Vec2(37,-15));
        spike7 = new Spike(this, getPlayer(), new Vec2(39,-15));
        spike8 = new Spike(this, getPlayer(), new Vec2(41,-15));

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
                        game.goNextLevel();
                        //System.out.println("Tick...");
                    } catch (IOException ex) {
                        Logger.getLogger(LevelOne.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                int randomNum = ThreadLocalRandom.current().nextInt(0, 100+1);
                if(randomNum<2)
                {
                    if(masterVolume > 0)
                    {
                        birdSound.play();
                        birdSound.setVolume(masterVolume*0.45);
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
        if(masterVolume > 0)
        {
            super.stopSound();
            jungleSound.close();
            birdSound.close();
            se.stopSound();
            se2.stopSound();
            se3.stopSound();
            se4.stopSound();
            se5.stopSound();
            spike.stopSound();
            spike2.stopSound();
            spike3.stopSound();
            spike4.stopSound();
            spike5.stopSound();
            spike6.stopSound();
            spike7.stopSound();
            spike8.stopSound();
        }
    }

    /**
     * Play Sound files
     */
    @Override
    public void playSound() 
    {
        if(masterVolume > 0)
        {
            super.playSound();
            jungleSound.loop();
        }
        tick();
    }

    /**
     * Pause Sound Files
     */
    @Override
    public void pauseSound() 
    {
        timer.cancel();
        if(masterVolume > 0)
        {
            super.pauseSound();
            jungleSound.pause();
            birdSound.pause();
            se.pauseSound();
            se2.pauseSound();
            se3.pauseSound();
            se4.pauseSound();
            se5.pauseSound();
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

        try {
            OptionManager optionsManager = new OptionManager();
            optionsManager.read();
            masterVolume = Double.parseDouble(optionsManager.getLine(0).substring(8));
            if(masterVolume > 0)
            {
                jungleSound = new SoundClip("data/Sound/Jungle/jungle.wav");
                birdSound = new SoundClip("data/Sound/Jungle/bird.wav");
                jungleSound.setVolume(masterVolume);
                birdSound.setVolume(masterVolume);
            }
            se.openSound();
            se2.openSound();
            se3.openSound();
            se4.openSound();
            se5.openSound();
            spike.openSound();
            spike2.openSound();
            spike3.openSound();
            spike4.openSound();
            spike5.openSound();
            spike6.openSound();
            spike7.openSound();
            spike8.openSound();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(LevelThree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(LevelThree.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(masterVolume > 0){
            jungleSound.loop();
        }
    }
    
}


