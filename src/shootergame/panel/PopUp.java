/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.panel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Pop up that notifies the user. A new window (JFrame) is created.
 * 
 * @author Tahmid
 */
public class PopUp extends JFrame
{
    
    private Container con;
    private GridBagConstraints gridConstraints = new GridBagConstraints();
    private String string;
    
    /**
     *
     * @param string
     * String that will be displayed on the JFrame window.
     */
    public PopUp(String string)
    {
        super();
        
        this.string = string;
        
        con = this.getContentPane();
        
        gridConstraints.insets = new Insets(10,10,10,10);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        
        setLayout(new GridBagLayout());
        
        //Label
        JLabel text = new JLabel(string);
        
        //Button
        JButton okButton = new JButton("OK");
        
        con.add(text, gridConstraints);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 10;
        con.add(okButton, gridConstraints);
        
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                dispose();
            }
            
            
        });
        
        setTitle("Message");
        setSize(400, 200);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth()-getWidth())/2;
        int y = (int) (dimension.getHeight()-getHeight())/2;
        this.setLocation(x, y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// quit the application when the game window is closed
        setVisible(true);
        setResizable(false);
        setAlwaysOnTop(true);
    }
    
    /**
     *
     * @return
     * string displayed on window.
     */
    public String getString()
    {
        return string;
    }
    
    /**
     *
     * @param string
     * Set string that will be displayed on JFrame.
     */
    public void setString(String string)
    {
        this.string = string;
    }
    
    
    
}
