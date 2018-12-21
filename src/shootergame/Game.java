/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import shootergame.panel.mainmenu.MainMenu;
import shootergame.world.GameFrame;
import shootergame.world.GameLevel;
import shootergame.world.GameView;
import shootergame.world.level.LevelOne;
import shootergame.world.level.LevelThree;
import shootergame.world.level.LevelTwo;

/**
 * Handles the game levels. Deals with switching levels, level restarts, closing levels 
 * as well as initiating levels.
 * 
 * The basic controls for the Player is D-Right, A-Left, SPACE - Jump, 
 * ENTER - Shoot, E - Climb Ladder.
 * 
 * @author Tahmid
 *
 *
 */
public class Game{
    
    private String title;
    private int width, height;
    
    private GameLevel world;
    private GameView view;
    private GameFrame frame;
    private int level = 1;
    
    //Player Controls mapping
    private InputMap im;
    private ActionMap am;
    
    /**
     * Constructor for the Game.
     * @param title
     * Windows Title.
     * @param width
     * Windows width.
     * @param height
     * Windows height.
     * @param frame
     * Frame used by windows.
     * @throws IOException
     * IO Error.
     */
    public Game(String title, int width, int height, GameFrame frame) throws IOException
    {
        this.title = title;
        this.width = width;
        this.height = height;
        this.frame = frame;
        
        switch(level)
        {
            case 1:
                world = new LevelOne();
                view = new GameView(world, width, height, "data/BG/b1.png");
                break;
            case 2:
                world = new LevelTwo();
                view = new GameView(world, width, height, "data/BG/LavaBackground.gif");
                break;
            case 3:
                world = new LevelThree();
                view = new GameView(world, width, height, "data/BG/Cave.png");
                break;
            default:
                System.exit(0);
                break;
        }
        //frame = new GameFrame(title, view, width, height, world, this);
        this.frame.getContentPane().add(view);
        this.frame.getContentPane().repaint();
        this.frame.getContentPane().validate();
        frame.setView(view);
        frame.setWorld(world);
        frame.setGame(this);
        world.populate(this, view, frame);
        view.setFrame(this.frame);
        view.setPlayer(world.getPlayer());
        //this.frame.debugFrame(world);
        
        
        
        im = view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        am = view.getActionMap();
        
        //When Keys are pressed...
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0 , false), "ESC");
        
        am.put("ESC", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                getFrame().startPauseMenu();
                //world.stop();
            }
        });
        world.tick();
        world.start();
    }
    
    /**
     *
     * @return
     * Title of window.
     */
    public String getTitle()
    {
        return title;
    }
    
    /**
     *
     * @return
     * Width of window.
     */
    public int getWidth()
    {
        return width;
    }
    
    /**
     *
     * @return
     * Height of window.
     */
    public int getHeight()
    {
        return height;
    }
    
    /**
     * Restart the game and repaint the window.
     * @throws IOException
     * IO Error.
     */
    public void restartGame() throws IOException
    {
        System.out.println("init...");
        world.stopSound();
        //frame.dispose();
        this.frame.getContentPane().remove(view);
        this.frame.getContentPane().repaint();
        this.frame.getContentPane().invalidate();
        world = null;
        view = null;
        
        
        switch(level)
        {
            case 1:
                world = new LevelOne();
                view = new GameView(world, width, height, "data/BG/b1.png");
                break;
            case 2:
                world = new LevelTwo();
                view = new GameView(world, width, height, "data/BG/LavaBackground.gif");
                break;
            case 3:
                world = new LevelThree();
                view = new GameView(world, width, height, "data/BG/Cave.png");
                break;
            default:
                System.exit(0);
                break;
        }
        
        //frame = new GameFrame(title, view, width, height, world, this);
        this.frame.getContentPane().add(view);
        this.frame.getContentPane().repaint();
        this.frame.getContentPane().validate();
        frame.setView(view);
        frame.setWorld(world);
        frame.setGame(this);
        world.populate(this, view, frame);
        view.setFrame(frame);
        view.setPlayer(world.getPlayer());
        //frame.debugFrame(world);
        
        
        im = view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        am = view.getActionMap();
        
        //When Keys are pressed...
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0 , false), "ESC");
        
        am.put("ESC", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                frame.startPauseMenu();
                world.stop();
            }
        });
        world.tick();
        world.start();
    }
    
    /**
     * Go to next level once objective is complete.
     * 
     * @throws IOException
     * IO Error
     */
    public void goNextLevel() throws IOException
    {
        world.stopTick();
        world.stop();
        world.stopSound();
        level = level + 1;
        //frame.dispose();
        this.frame.getContentPane().remove(view);
        this.frame.getContentPane().repaint();
        this.frame.getContentPane().invalidate();
        world = null;
        view = null;
        
        switch(level)
        {
            case 1:
                world = new LevelOne();
                view = new GameView(world, width, height, "data/BG/b1.png");
                break;
            case 2:
                world = new LevelTwo();
                view = new GameView(world, width, height, "data/BG/LavaBackground.gif");
                break;
            case 3:
                world = new LevelThree();
                view = new GameView(world, width, height, "data/BG/Cave.png");
                break;
            default:
                new MainMenu(title, width, height, frame);
                System.gc();
                break;
        }
        
        //frame = new GameFrame(title, view, width, height, world, this);
        try{
        this.frame.getContentPane().add(view);
        this.frame.getContentPane().repaint();
        this.frame.getContentPane().validate();
        frame.setView(view);
        frame.setWorld(world);
        frame.setGame(this);
        world.populate(this, view, frame);
        view.setFrame(frame);
        view.setPlayer(world.getPlayer());
        }catch(NullPointerException npe){}
        //frame.debugFrame(world);
        
        
        im = view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        am = view.getActionMap();
        
        //When Keys are pressed...
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0 , false), "ESC");
        
        am.put("ESC", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
                getFrame().getContentPane().remove(view);
                getFrame().getContentPane().repaint();
                getFrame().getContentPane().invalidate();
                frame.startPauseMenu();
                world.stop();
            }
        });
        world.tick();
        world.start();
    }

    /**
     *
     * @return
     * Frame
     */
    public GameFrame getFrame() 
    {
        return frame;
    }
    
    
}
