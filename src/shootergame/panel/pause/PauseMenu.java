/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.panel.pause;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import shootergame.Game;
import shootergame.panel.mainmenu.MainMenu;
import shootergame.world.GameFrame;
import shootergame.world.GameLevel;
import shootergame.world.GameView;

/**
 * Pause Menu that loads mid-session through the game.
 * 
 * @author Tahmid
 */
public class PauseMenu extends JPanel
{
    
    private GameFrame frame;
    private GridBagConstraints gridConstraints = new GridBagConstraints();
    private GameView view;
    private GameLevel world;
    private Game game;
    private ImageIcon resumeImage;
    private ImageIcon restartImage;
    private ImageIcon quitImage;
    
    /**
     * Constructor for PauseMenu
     * @param frame
     * Frame the window uses.
     * @param view
     * View the window uses.
     * @param world
     * World that will be paused (during construction) and played (when option menu removed).
     * @param game
     * Game would be used to pause or restart the world.
     */
    public PauseMenu(GameFrame frame, GameView view, GameLevel world, Game game)
    {
        super();
        this.frame = frame;
        this.view = view;
        this.world = world;
        this.game = game;
        
        this.setBackground(new Color(0,96,255));
        
        resumeImage = new ImageIcon("data/Menu/PauseMenu/Resume.png");
        restartImage = new ImageIcon("data/Menu/PauseMenu/Restart.png");
        quitImage = new ImageIcon("data/Menu/quit.png");

        if(world != null)
        {
            world.stopSound();
            world.stop();
        }else
            {
                return;
            }

        setLayout(new GridBagLayout());
        
        //defaults
        gridConstraints.gridx = 0;
        gridConstraints.gridy = -10;
        gridConstraints.insets = new Insets(10,10,10,10);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        
        //Buttons
        JButton resume = new JButton(resumeImage);
        JButton restart = new JButton(restartImage);
        JButton quit = new JButton(quitImage);
        
        //get rid of the default button look...
        resume.setBorderPainted(false);
        resume.setFocusPainted(false);
        resume.setContentAreaFilled(false);
        
        restart.setBorderPainted(false);
        restart.setFocusPainted(false);
        restart.setContentAreaFilled(false);
       
        quit.setBorderPainted(false);
        quit.setFocusPainted(false);
        quit.setContentAreaFilled(false);
        
        //Add buttons to menu
        add(resume,gridConstraints);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 10;
        add(restart, gridConstraints);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 20;
        add(quit,gridConstraints);
        
        this.frame.getContentPane().add(this, BorderLayout.CENTER);
        this.frame.getContentPane().repaint();
        this.frame.getContentPane().validate();
        
        resume.addActionListener(new Resume(this,frame,this.view, world));
        restart.addActionListener(new Restart(this.game, this));
        quit.addActionListener(new Quit());
    }

    private class Quit implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            //System.exit(0);
           world.stopTick();
           frame.getContentPane().removeAll();
           frame.getContentPane().repaint();
           frame.getContentPane().invalidate();
           

           new MainMenu(frame.getTitle(),frame.getWidth(),frame.getHeight(),frame);

        }
    }
    
    private class Resume implements ActionListener
    {
        
        private JPanel panel;
        private GameFrame frame;
        private GameView view;
        private GameLevel world;
                
        public Resume(JPanel panel, GameFrame frame, GameView view, GameLevel world)
        {
            this.panel = panel;
            this.frame = frame;
            this.view = view;
            this.world = world;
            
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) 
        {
           //frame.remove(panel);
           frame.getContentPane().removeAll();
           frame.getContentPane().repaint();
           frame.getContentPane().invalidate();
           //frame.add(view);
           frame.getContentPane().add(view);
           frame.getContentPane().repaint();
           frame.getContentPane().invalidate();

           world.start();
           world.tick();
            try {
                world.openSound();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(PauseMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PauseMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class Restart implements ActionListener
    {
        
        private Game game;
        private JPanel panel;
        
                
        public Restart(Game game, JPanel panel)
        {
            this.game = game;
            this.panel = panel;
            world.stopTick();
        }
        
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            
            try {
                //frame.remove(panel);
                frame.getContentPane().removeAll();
                frame.getContentPane().repaint();
                frame.getContentPane().invalidate();
                
                
                game.restartGame();
            } catch (IOException ex) {
                Logger.getLogger(PauseMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
         

        }
    }
}


