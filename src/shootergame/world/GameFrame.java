/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.world;

import city.cs.engine.DebugViewer;
import city.cs.engine.UserView;
import city.cs.engine.World;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import shootergame.Game;
import shootergame.panel.pause.PauseMenu;


/**
 * Main Window Frame the application uses.
 * 
 * @author Tahmid
 */
public class GameFrame extends JFrame{
    
    private JFrame debugView;
    private GameView view;
    private GameLevel world;
    private PauseMenu menu;
    
    private Game game;
    private Container con;
    
    private int width;
    private int height;
    
    /**
     * Constructor for GameFrame.
     * @param winName
     * Title for window.
     * @param view
     * View to be used by Frame.
     * @param width
     * Width of the Frame.
     * @param height
     * Height of the Frame.
     * @param world
     * World to parse to option Menu.
     * @param game
     * Game to parse to option Menu.
     * @throws IOException
     * IO Error.
     */
    public GameFrame(String winName, GameView view, int width, int height
            , GameLevel world, Game game) throws IOException
    {
        super(winName);
        this.view = view;
        this.world = world;
        this.game = game;
        
        this.width = width;
        this.height = height;
        
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth()) - width)/2;
        int y = (int) ((dimension.getHeight()) - height)/2;
        
        con = this.getContentPane();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// quit the application when the game window is closed
        setLocation(x,y);//Centre the window
        con.add(view);// display the world in the window
        setResizable(false);// don't let the game window be resized

        pack();// size the game window to fit the world view
        
        setVisible(true);// make the window visible
        //debugView = new DebugViewer(world, 500, 500);
        
    }
    
    /**
     * Constructor for GameFrame.
     * @param winName
     * Title for window.
     * @param width
     * Width of the window.
     * @param height
     * Height of the window.
     * @param panel
     * panel the window will be using.
     * @throws IOException
     * IO Error.
     */
    public GameFrame(String winName, int width, int height, JPanel panel) throws IOException
    {
        super(winName);
       
        this.width = width;
        this.height = height;
        
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth()) - width)/2;
        int y = (int) ((dimension.getHeight()) - height)/2;
        
        con = this.getContentPane();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// quit the application when the game window is closed
        setLocation(x,y);//Centre the window
        setResizable(false);// don't let the game window be resized

        setSize(width,height);
        
        setVisible(true);// make the window visible
        con.add(panel);
        //debugView = new DebugViewer(world, 500, 500);
        
    }
    
    /**
     * Debug frame that only shows the shapes of objects. Shows all the physics 
     * aspect of the World.
     * @param world
     */
    public void debugFrame(World world)
    {
        debugView = new DebugViewer(world, 500, 500);
    }
    
    /**
     * Start Pause Menu.
     */
    public void startPauseMenu()
    {
        menu = new PauseMenu(this, view, world, game);
        
        this.getContentPane().removeAll();
        this.getContentPane().repaint();
        this.getContentPane().invalidate();

        this.getContentPane().add(menu);
        this.getContentPane().repaint();
        this.getContentPane().validate();
    }
    
    /**
     *
     * @return
     * Pause Menu
     */
    public PauseMenu getPauseMenu()
    {
        return menu;
    }
    
    /**
     *
     * @param view
     * Set View.
     */
    public void setView(GameView view)
    {
        this.view = view;
    }
    
    /**
     *
     * @param world
     * Set World.
     */
    public void setWorld(GameLevel world)
    {
        this.world = world;
    }
    
    /**
     *
     * @param width
     * Set Width.
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     *
     * @param height
     * Set Height.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     *
     * @param game
     * Set Game.
     */
    public void setGame(Game game) {
        this.game = game;
    }
    
    
}
