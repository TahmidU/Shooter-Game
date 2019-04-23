/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.panel.mainmenu;

import city.cs.engine.SoundClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import shootergame.filemanager.OptionManager;
import shootergame.panel.PopUp;
import shootergame.world.GameFrame;

/**
 * Load Options Menu. Writes to txt file which the Level read to configure the window
 * sound.
 * 
 * @author Tahmid
 */
public class Option extends JPanel
{
    
    private GridBagConstraints gridConstraints = new GridBagConstraints();
    private GameFrame frame;
    private String[] resList = {"800x600","1280x720","1920x1080"};
    private Font font = new Font("Times New Roman", Font.BOLD, 30);
    private JLabel resLabel;
    private JLabel volumeLabel;
    private JComboBox resOp;
    private JSlider volumeSlider;
    private SoundClip releaseSlider;
    private OptionManager op;
    
    /**
     * Constructor for Option.
     * 
     * @param winName
     * Title for window.
     * @param width
     * Width for window.
     * @param height
     * Height for window.
     * @param frame
     * Frame used by the window.
     * @throws IOException
     * IO Error.
     * @throws UnsupportedAudioFileException
     * Audio unsupported.
     * @throws LineUnavailableException
     * .getLine() Error.
     */
    @SuppressWarnings({"Convert2Lambda", "LeakingThisInConstructor", "Convert2Lambda", "LeakingThisInConstructor"})
    public Option(String winName, int width, int height, GameFrame frame)
            throws IOException, UnsupportedAudioFileException, LineUnavailableException
    {
        this.setBackground(new Color(0,96,255));
        releaseSlider = new SoundClip("data/Sound/Options/WindowsBackground.wav");
        
        gridConstraints.insets = new Insets(10,10,10,10);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        
        OptionManager optionsManager = new OptionManager();
        optionsManager.read();
        
        setLayout(new GridBagLayout());
        
        //Buttons
        JButton back = new JButton(new ImageIcon("data/Menu/OptionMenu/back.png"));
        
        //ComboBox
        resOp = new JComboBox(resList);
        resOp.setSize(20, 20);
        switch(optionsManager.getLine(1).substring(12))
        {
            case "800x600":
                resOp.setSelectedIndex(0);
                break;
            case "1280x720":
                resOp.setSelectedIndex(1);
                break;
            case "1920x1080":
                resOp.setSelectedIndex(2);
                break;
        }
        
        //Slider
        volumeSlider = new JSlider(0,100);
        volumeSlider.setValue((int) (Float.parseFloat(optionsManager.getLine(0).substring(8))*100));
        
        //Label
        resLabel = new JLabel("Resolution: ");
        resLabel.setFont(font);
        
        volumeLabel = new JLabel("Volume: ");
        volumeLabel.setFont(font);
        
        //get rid of the default button look...
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        back.setContentAreaFilled(false);
        
        add(resLabel, gridConstraints);
        gridConstraints.gridx = 5;
        gridConstraints.gridy = 0;
        add(resOp, gridConstraints);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 10;
        add(volumeLabel, gridConstraints);
        gridConstraints.gridx = 5;
        gridConstraints.gridy = 10;
        add(volumeSlider, gridConstraints);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 20;
        add(back, gridConstraints);
        
        
        
        //frame = new GameFrame(winName, width, height);
        this.frame = frame;
        //frame.add(this,BorderLayout.CENTER);
        this.frame.getContentPane().add(this, BorderLayout.CENTER);
        this.frame.getContentPane().repaint();
        this.frame.getContentPane().validate();
        
        resOp.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {

                new PopUp("Click Back to apply.");
                OptionManager optionsManager = new OptionManager();
                optionsManager.read();
                switch((String)resOp.getSelectedItem())
                {
                        case "800x600":
                            optionsManager.replaceString(optionsManager.getLine(1), "resolution: 800x600");
                            break;
                        case "1280x720":
                            optionsManager.replaceString(optionsManager.getLine(1), "resolution: 1280x720");
                            break;
                        case "1920x1080":
                            optionsManager.replaceString(optionsManager.getLine(1), "resolution: 1920x1080");
                            break;
                    }

            }
            
            
        });
        
        volumeSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent ce)
            {
                OptionManager optionsManager = new OptionManager();
                optionsManager.read();
                optionsManager.replaceString(optionsManager.getLine(0), "volume: " + (double) volumeSlider.getValue()/100);
            }
            
        });
        
        volumeSlider.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent me) {}
            
            @Override
            public void mousePressed(MouseEvent me) {}
            
            @Override
            public void mouseEntered(MouseEvent me) {}
            
            @Override
            public void mouseExited(MouseEvent me) {}
            
            @Override
            public void mouseReleased(MouseEvent me)
            {
                try{
                    releaseSlider.setVolume((double) volumeSlider.getValue()/100);
                }catch(IllegalArgumentException ie){}
                System.out.println((double) volumeSlider.getValue()/100);
                if(!(volumeSlider.getValue() <= 0)){
                    releaseSlider.play();
                }else{}
            }
            
            
        });
        
        back.addActionListener(new ActionListener()
        {
            @Override
            @SuppressWarnings("ResultOfObjectAllocationIgnored")
            public void actionPerformed(ActionEvent ae)
            {
                //frame.dispose();
                
                int winX = 766;
                int winY = 0;
                
                

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
                    //System.out.print(optionsManager.getLine(1));
                    //new MainMenu(winName, winX, winY);
                destroyOption();
                new MainMenu(winName, winX, winY, frame);
            }
            
            
        });
        
        
    }
    
    /**
     * Remove option. Remove from pane, repaint and invalidate the current pane.
     */
    public void destroyOption()
    {

        frame.getContentPane().remove(this);
        frame.getContentPane().repaint();
        frame.getContentPane().invalidate();
        System.out.println("option removed...");
    }
    
}
