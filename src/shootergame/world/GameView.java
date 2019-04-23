/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame.world;

import city.cs.engine.UserView;
import city.cs.engine.World;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.swing.ImageIcon;
import shootergame.filemanager.OptionManager;
import shootergame.interactor.Player;
import shootergame.weapon.gun.MachineGun;
import shootergame.weapon.gun.Pistol;
import shootergame.weapon.gun.Shotgun;

/**
 * Acts as the camera to the World.
 * 
 * @author Tahmid
 */
public class GameView extends UserView{
    
    //Window Resolution
    private int winX;
    private int winY;
    
    //world, width, height, "data/BG/Cave.png"
    
    private final Image bk;
    String imageDirectory;
    
    private World world;
     
    private GameFrame frame;
    
    private Player player;
    
    //GUI - Ammo and Health
    private String health = "Health";
    private String ammo = "Ammo";
    private Font GUIFont;
    
    //GUI - Weapon
    private MachineGun mg;
    private Pistol p;
    private Shotgun s;
    private Font ClipFont = new Font("Times New Roman", Font.BOLD, 20);
    private int offset = 0;
    
    //private Graphics2D g;
    
    //The object positioning works relativly to the positions of object in 1280x720 object placement
    private float relX = 1;
    private float relY = 1;
    
    /**
     * Constructor for GameView.
     * @param world
     * World
     * @param winX
     * Width of view.
     * @param winY
     * Height of view.
     * @param imageDirectory
     * Background image directory.
     */
    public GameView(World world, int winX, int winY, String imageDirectory)
    {
        super(world, winX, winY);
        this.imageDirectory = imageDirectory;
        bk = new ImageIcon(imageDirectory).getImage();
        this.world = world;
        this.winX = winX;
        this.winY = winY;
        

        OptionManager optionsManager = new OptionManager();
        optionsManager.read();
        switch(optionsManager.getLine(1).substring(12))
        {
                case "800x600":
                    relX = 0.92f;
                    relY = 0.92f;
                    offset = 50;
                    break;
                case "1280x720":
                    relX = 1f;
                    relY = 1f;
                    offset = 0;
                    break;
                case "1920x1080":
                    relX = (1920f*0.8f)/1280f;
                    relY = (1080f*0.8f)/720f;
                    offset = 0;
                    break;
                default:
        }
        
        GUIFont = new Font("Times New Roman", Font.BOLD, Math.round(50*relX));
    }
    
    /**
     * Handles the GUI drawing and the view translating (acts as a camera).
     * @param g
     * Graphics2D
     */
    @Override
    @SuppressWarnings("empty-statement")
    public void paintBackground(Graphics2D g)
    {
        super.paintBackground(g);
        
        
        try{
            //Paints the background with an Image.
            g.drawImage(bk,0, 0,frame.getWidth(),frame.getHeight(), null);
            g.scale(relX, relY);
            //System.out.println(frame.getHeight());
            
            //Paint Text to screen: the Health and Ammo.
            g.setFont(GUIFont);
            g.drawString(health, 0, (frame.getHeight()/8));
            g.drawString(ammo, 0, (frame.getHeight()/4));
            
            //Paint the Health Bar and Ammo Bar
            g.setColor(Color.RED);
            g.fillRect((frame.getWidth()/8) + offset, (frame.getHeight()/12), ((200*player.getHealth())/100), 30);
            g.setColor(Color.BLUE);
            g.setFont(ClipFont);
            switch(player.getGun())
            {
                case "mg":
                    g.fillRect((frame.getWidth()/8) + offset, frame.getHeight()/5, (200*mg.getAmmo())/120, 30);
                    g.setColor(Color.BLACK);
                    g.drawString("Clips: " + mg.getClip(), (frame.getWidth()/8) + offset , (frame.getHeight()/4)+10);
                    break;
                case "p":
                    g.fillRect((frame.getWidth()/8) + offset, frame.getHeight()/5, (200*p.getAmmo())/30, 30);
                    g.setColor(Color.BLACK);
                    g.drawString("Clips: " + p.getClip(), (frame.getWidth()/8) + offset , (frame.getHeight()/4)+10);
                    break;
                case "s":
                    g.fillRect((frame.getWidth()/8) + offset, frame.getHeight()/5, (200*s.getAmmo())/15, 30);
                    g.setColor(Color.BLACK);
                    g.drawString("Clips: " + s.getClip(), (frame.getWidth()/8) + offset , (frame.getHeight()/4)+10);
                    break;
            }
            
            
            //NOTE:Draw GUI to screen before the translate otherwise the GUI will be
            //moving around the world space.
            
            //View follows the Player. View will be translated.
            g.translate(((-this.player.getPlayerXPos())+(this.player.getStartPos().x))*this.player.getSpeed()*2, 0);
            //System.out.println(this.player.getPlayerXPos());
        }catch(NullPointerException e){}//Because the view is created before the frame is created...
        
        
    }
    
    /**
     * Used to paint components on screen.
     * @param g
     * Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
    }
    
    /**
     *
     * @param frame
     * Set Frame.
     */
    public void setFrame(GameFrame frame)
    {
        this.frame = frame;
    }
    
    /**
     *
     * @return
     * Frame
     */
    public GameFrame getFrame()
    {
        return this.frame;
    }
    
    /**
     *
     * @return
     * View
     */
    public UserView getUserView()
    {
        return this;
    }
    
    /**
     *
     * @return
     * Background Image.
     */
    public String getImageDirectory() {
        return imageDirectory;
    }
    
    /**
     *
     * @param imageDirectory
     * Set Background Image.
     */
    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }
    
    /**
     *
     * @param world
     * Set World.
     */
    public void setWorld(World world)
    {
        this.world = world;
    }
    
    /**
     *
     * @return
     * Width of view.
     */
    public int getWinX() {
        return winX;
    }
    
    /**
     *
     * @param winX
     * Set width of view.
     */
    public void setWinX(int winX) {
        this.winX = winX;
    }
    
    /**
     *
     * @return
     * Height of view.
     */
    public int getWinY() {
        return winY;
    }
    
    /**
     *
     * @param winY
     * Set Height of view.
     */
    public void setWinY(int winY) {
        this.winY = winY;
    }
    
    /**
     *
     * @param player
     * Set Player 
     */
    public void setPlayer(Player player)
    {
        this.player = player;
    }
    
    /**
     *
     * @param mg
     * set MG Weapon.
     */
    public void setMg(MachineGun mg)
    {
        this.mg = mg;
    }
    
    /**
     *
     * @param p
     * Set Pistol Weapon.
     */
    public void setP(Pistol p)
    {
        this.p = p;
    }
    
    /**
     *
     * @param s
     * Set Shotgun Weapon.
     */
    public void setS(Shotgun s)
    {
        this.s = s;
    }
    
    
}
