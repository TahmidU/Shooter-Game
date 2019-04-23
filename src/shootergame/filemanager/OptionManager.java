package shootergame.filemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Manages the option txt file.
 * @author Tahmid
 */
public class OptionManager
{
    private File options;
    private FileReader optionsReader;
    private BufferedReader bufferedFile;
    private final ArrayList<String> elements;
    
    /**
     * Constructor for OptionManager class.
     * Opens a option.txt file and initialises an ArrayList.
     * If file does not exist it will be created and default setting will be written.
     */
    public OptionManager()
    {
        options = new File("data/config/option.txt");
        elements = new ArrayList<>();
        
        if(!options.exists())
        {
            System.out.println("File does not exist");
            try {
                PrintWriter writer = new PrintWriter("data/config/option.txt", "UTF-8");
            //defaults
            writer.format("%s%s", "volume: ", "30");
            writer.format("%n%s%s", "resolution: ", "1280x720");
            System.out.println("File Created");
            writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    /**
     * Reads the contents from the txt file and pushes each line into an array.
     */
    public void read()
    {
        try {
            optionsReader = new FileReader(options);
        bufferedFile = new BufferedReader(optionsReader);
        String line;
        while((line = bufferedFile.readLine()) != null)
        {
            elements.add(line);
        }
        optionsReader.close();
        bufferedFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Allows to pick a line from the txt file and return as a string.
     * 
     * @param index position in list (0 is the start and n is the end).
     * 
     * @return
     * the position of the line you specified.
     */
    public String getLine(int index)
    {
        return elements.get(index);
    }
    
    /**
     * Read all content from the txt file.
     */
    public void readAll()
    {
        for(int i = 0; i<elements.size();i++)
        {
            System.out.println(elements.get(i));
        }
    }
    
    /**
     * Initialises the FileReader and BufferedReader.
     * @return
     * The BufferedReader
     */
    public BufferedReader bufferedReader()
    {
        try {
            optionsReader = new FileReader(options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bufferedFile = new BufferedReader(optionsReader);
        
        return bufferedFile;
    }
    
    /**
     * Closes the FileReader and BufferedReader.
     * @throws IOException
     * If error occurred while reading or writing.
     */
    public void closeBufferedReader() throws IOException
    {
        optionsReader.close();
        bufferedFile.close();
    }

    /**
     *
     * Allows to replace a String in a txt file by pushing all contents into 
     * a String variable that stores the original contents of the txt 
     * then using a new String variable that contains the modified content using the .replace() method.
     * After that the bufferedWritter writes to the file location.
     * 
     * @param string 
     * String intended to replace
     * 
     * @param string1
     * String intended to replace with
     */
    public void replaceString(String string, String string1)
    {
        try {
            optionsReader = new FileReader(options);

        bufferedFile = new BufferedReader(optionsReader);
        
        String line = bufferedFile.readLine();
        String originalContent = "";
        String modifiedContent;
        
        while(line != null)
        {
            originalContent += line + System.lineSeparator();
            line = bufferedFile.readLine();
        }
        
        modifiedContent = originalContent.replaceFirst(string, string1);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("data/config/option.txt"));
        bufferedWriter.write(modifiedContent);
        
        optionsReader.close();
        bufferedFile.close();
        bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
