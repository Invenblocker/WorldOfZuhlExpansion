package database;

import acq.ILoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class txtLoader implements ILoader
{
    private String[] roomsInfo;
    private String[] roomPositionInfo;
    private String[] itemsInfo;
    private String[] specialItemsInfo;
    private List<String[]> exitInfo;
    private String[] playerInfo;
    private String[] saboteurInfo;
    private String[] helperInfo;
    private String[] timeHolderInfo;
    private int roomsRepaired;
    
    private LinkedHashMap<String, Integer> highScore;
    
    public txtLoader()
    {
        this.highScore = new LinkedHashMap<>();
        this.roomsRepaired = 0;
    }

    /**
     * Takes the name of a txt file containing rooms and their exits, and items 
     * and their room.Then puts the rooms into the rooms HashMap with their 
     * exits, and puts the items into the items HashMap, with their respective 
     * rooms. The following loadGame method, does exactly the same, the 
     * difference is that a saved game is loaded instead of the the default txt- 
     * file.
     * @param gameName The url to the file which should be loaded
     */
    @Override
    public void newGame(String gameName) 
    {
        exitInfo = new ArrayList<>();
        initializeGame(gameName);
    }
    
    @Override
    public void loadGame(String gameName)
    {
        exitInfo = new ArrayList<>();
        initializeGame(gameName);  
    }
    
    /**
     * A scanner is used to read in all the stored text strings in the file. 
     * The method ensures that the information are stored correctly in our prog-
     * ram after being initialized. Dis is done by checking for the keywords, at
     * index 0 and storing them into ther respective arrays, which is used to 
     * manipulate the data in our string converter class.
     * @param gameName The url to the file which should be loaded
     */
    private void initializeGame (String gameName)
    {
        Scanner sc = null;
        
        try
        {
            sc = new Scanner(new File(gameName));
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(txtLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(sc.hasNext())
        {
            String line = sc.nextLine();  
            String[] words = line.split(" ");
            if(words[0].equals("Room:")){      // adds all rooms to the hashmap rooms.
                roomsInfo = words;
            }
            else if(words[0].equals("Item:")){
                itemsInfo = words;
            }
            else if(words[0].equals("Player:")){// mangler noget, special item og item? defaultRoom
                playerInfo = words;
            }
            else if(words[0].equals("Saboteur:")){
                saboteurInfo = words;
            }
            else if(words[0].equals("Helper:")) {
                helperInfo = words;
            }
            else if(words[0].equals("SpecialItem:")){
                specialItemsInfo = words;
            }
            else if(words[0].equals("RoomsRepaired:")){
                roomsRepaired = Integer.parseInt(words[1]);
            }
            else if(words[0].equals("TimeHolder:")){
                timeHolderInfo = words;
                 }
            else if(words[0].equals("RoomPos:")){
                roomPositionInfo = words;
            }
            else{
                exitInfo.add(words);
            }
        }
    }
    
    @Override
    public String[] getRoomsInfo() {return roomsInfo;}
    
    @Override
    public String[] getRoomPositionInfo() {return roomPositionInfo;}
    
    @Override
    public String[] getItemsInfo() {return itemsInfo;}
    
    @Override
    public String[] getSpecialItemsInfo() {return specialItemsInfo;}
    
    @Override
    public List<String[]> getExitInfo() {return exitInfo;}

    @Override
    public String[] getPlayerInfo() {return playerInfo;}

    @Override
    public String[] getSaboteurInfo() {return saboteurInfo;}

    @Override
    public String[] getHelperInfo() {return helperInfo;}

    @Override
    public String[] getTimeHolderInfo() {return timeHolderInfo;}
    
    @Override
    public int getRoomsRepaired () {return roomsRepaired;}
    
      /**
     * This method is used to creater a new txt file to store a highscore.
     * A linked hashmap is used, to retain the order of the highscore, whenever 
     * a new highscore is added to the file.
     * The highscore is stored with a player name, and a int value for the score.
     * @return The map containing information about all highscores
     */
    @Override
    public LinkedHashMap<String, Integer> getHighscore() { 
        this.highScore = new LinkedHashMap<> ();
        int score = (int)Double.NaN;
        
        Scanner sc = null;
        try
        {
            sc = new Scanner (new File("assets/maps/highscore.txt"));
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(txtLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] words = line.split (":");
            
            String name = words[0];
            
            try
            {
                score = Integer.parseInt(words[1]);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Highscore does not have the right format");
                e.printStackTrace();
            }
            
            highScore.put(name, score);
        }
        
        return  highScore;
    }
    
}