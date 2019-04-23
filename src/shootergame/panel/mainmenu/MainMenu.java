/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.panel.mainmenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import shootergame.Game;
import shootergame.filemanager.OptionManager;
import shootergame.world.GameFrame;

/**
 * Create Main Menu. This is the first JPanel to load.
 * 
 * @author Tahmid
 */
public class MainMenu extends JPanel
{
    
    private GridBagConstraints gridConstraints = new GridBagConstraints();
    private GameFrame frame;
    private String stringRes;
    private Game game;
    
    /**
     * Constructor for Main Menu..
     * 
     * @param winName
     * Title of window.
     * @param width
     * width of the window.
     * @param height
     * height of the window.
     * @throws IOException
     * IO Error.
     */
    public MainMenu(String winName, int width, int height)
    {
        super();
        this.setBackground(new Color(0,96,255));
        
        gridConstraints.gridx = 0;
        gridConstraints.insets = new Insets(10,10,10,10);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        
        //Buttons
        JButton play = new JButton(new ImageIcon("data/Menu/MainMenu/play.png"));
        //JButton score = new JButton(new ImageIcon("data/Menu/MainMenu/score.png"));
        JButton option = new JButton(new ImageIcon("data/Menu/MainMenu/option.png"));
        JButton quit = new JButton(new ImageIcon("data/Menu/quit.png"));
        
        //get rid of the default button look...
        play.setBorderPainted(false);
        play.setFocusPainted(false);
        play.setContentAreaFilled(false);
        
        //score.setBorderPainted(false);
        //score.setFocusPainted(false);
        //score.setContentAreaFilled(false);
        
        option.setBorderPainted(false);
        option.setFocusPainted(false);
        option.setContentAreaFilled(false);
        
        quit.setBorderPainted(false);
        quit.setFocusPainted(false);
        quit.setContentAreaFilled(false);
        
        setLayout(new GridBagLayout());
        
        //Add buttons to menu
        add(play,gridConstraints);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 10;
        add(option, gridConstraints);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 20;
        add(quit,gridConstraints);
        
        frame = new GameFrame(winName,width,height,this);
        //frame.add(this, BorderLayout.CENTER);
        frame.getContentPane().add(this, BorderLayout.CENTER);
        
        //resume.addActionListener(new PauseMenu.Resume(this,frame,view, world));
        //restart.addActionListener(new PauseMenu.Restart(game, this));
        
        play.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                
                try{
                    
                    destroyMainMenu();
                    //frame.dispose();
                    
                    //default
                    int winX=1280;
                    int winY=720;
                    OptionManager optionsManager = new OptionManager();
                    optionsManager.read();
                    switch(optionsManager.getLine(1).substring(12))
                    {
                        case "800x600":
                            winX = 800;
                            winY = 600;
                            break;
                        case "1280x720":
                            winX = 1280;
                            winY = 720;
                            break;
                        case "1920x1080":
                            winX = 1920;
                            winY = 1080;
                            break;
                        default:
                    }
                    
                    game = new Game("Rumble", winX, winY, getFrame());
                    
                }catch(IOException ex){}
            }
            
            
        });
        
        option.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                //frame.dispose();
                
                try {
                    destroyMainMenu();
                    new Option(winName, width, height, frame);
                    
                } catch (IOException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            
        });
        
        quit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                System.exit(0);
            }
            
        });
        
    }
    
    /**
     * Constructor for Main Menu.
     * 
     * @param winName
     * Title of the window.
     * @param width
     * Width of the window.
     * @param height
     * Height of the window.
     * @param frame
     * Frame the window uses.
     * @throws IOException
     * IO Error.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public MainMenu(String winName, int width, int height, GameFrame frame)
    {
        super();
        this.setBackground(new Color(0,96,255));
        stringRes = frame.getWidth() + "x" + frame.getHeight();
        //System.out.println(stringRes);
        
        gridConstraints.gridx = 0;
        gridConstraints.insets = new Insets(10,10,10,10);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        
        //Buttons
        JButton play = new JButton(new ImageIcon("data/Menu/MainMenu/play.png"));
        //JButton score = new JButton(new ImageIcon("data/Menu/MainMenu/score.png"));
        JButton option = new JButton(new ImageIcon("data/Menu/MainMenu/option.png"));
        JButton quit = new JButton(new ImageIcon("data/Menu/quit.png"));
        
        //get rid of the default button look...
        play.setBorderPainted(false);
        play.setFocusPainted(false);
        play.setContentAreaFilled(false);
        
        //score.setBorderPainted(false);
        //score.setFocusPainted(false);
        //score.setContentAreaFilled(false);
        
        option.setBorderPainted(false);
        option.setFocusPainted(false);
        option.setContentAreaFilled(false);
        
        quit.setBorderPainted(false);
        quit.setFocusPainted(false);
        quit.setContentAreaFilled(false);
        
        setLayout(new GridBagLayout());
        
        //Add buttons to menu
        add(play,gridConstraints);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 10;
        add(option, gridConstraints);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 20;
        add(quit,gridConstraints);
        
        
        //frame = new GameFrame(winName,width,height,this);
        //frame.add(this, BorderLayout.CENTER);
        OptionManager optionsManager = new OptionManager();
        optionsManager.read();
        
        if(stringRes.equals(optionsManager.getLine(1).substring(12)))
        {
            this.frame = frame;
            
        }else{
            frame.dispose();
            this.frame = new GameFrame(winName,width,height,this);
        }
        
        
        this.frame.getContentPane().add(this, BorderLayout.CENTER);
        this.frame.getContentPane().repaint();
        this.frame.getContentPane().validate();
        
        
        //resume.addActionListener(new PauseMenu.Resume(this,frame,view, world));
        //restart.addActionListener(new PauseMenu.Restart(game, this));
        
        play.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                
                try{
                    
                    destroyMainMenu();
                    //frame.dispose();
                    
                    //default
                    int winX=1280;
                    int winY=720;
                    OptionManager optionsManager = new OptionManager();
                    optionsManager.read();
                    switch(optionsManager.getLine(1).substring(12))
                    {
                        case "800x600":
                            winX = 800;
                            winY = 600;
                            break;
                        case "1280x720":
                            winX = 1280;
                            winY = 720;
                            break;
                        case "1920x1080":
                            winX = 1920;
                            winY = 1080;
                            break;
                        default:
                    }
                    new Game("Rumble", winX, winY, getFrame());
                }catch(IOException ex){}
            }
            
            
        });
        
        option.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                //frame.dispose();
                
                try {
                    destroyMainMenu();
                    new Option(winName, width, height, getFrame());
                    
                } catch (IOException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
        });
        
        quit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                System.exit(0);
            }
            
        });
        
    }
    
    /**
     * Remove Main Menu. Repaint and invalidate the current pane.
     */
    public void destroyMainMenu()
    {
        //frame.getContentPane().remove(this);
        this.frame.getContentPane().remove(this);
        this.frame.getContentPane().repaint();
        this.frame.getContentPane().invalidate();
        System.out.println("Menu removed...");
    }
    
    /**
     *
     * @return
     * GameFrame - JPanel
     */
    public GameFrame getFrame()
    {
        return this.frame;
    }
}
