/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shootergame;

import java.io.IOException;
import shootergame.filemanager.OptionManager;
import shootergame.panel.PopUp;
import shootergame.panel.mainmenu.MainMenu;

/**
 *
 * @author Tahmi
 */
public class Launcher {
    
    /**
     * @param args the command line arguments
     */
    private static MainMenu mainMenu;
    private static OptionManager optionsManager;
    
    /**
     *
     * @param args
     * @throws IOException
     * IO Error
     */
    public static void main(String[] args) throws IOException
    {
        // TODO code application logic here
        
        
        int winX = 1280;
        int winY = 720;
        
        optionsManager = new OptionManager();
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
        mainMenu = new MainMenu("Rumble",winX, winY);

    }
    
    
}
