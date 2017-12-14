/*
 * This class is responsible for writing the current state of the game, into a
 * txt file. The saved txt file can then be loaded by the txtLoader class.
 */
package database;

import acq.IWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Loc
 */
public class txtWriter implements IWriter {

    public txtWriter() {}

    
    /**
     * This method is used to store all the needed data of the current game.
     * @param roomsInfo
     * @param itemsInfo
     * @param specialItemsInfo
     * @param exitInfo
     * @param playerInfo
     * @param saboteurInfo
     * @param helperInfo
     * @param timeHolderInfo
     * @param roomsRepaired
     * @param roomPosition 
     */
    @Override
    public void saveGame(String roomsInfo, String itemsInfo,String specialItemsInfo,
            List<String> exitInfo, String playerInfo, String saboteurInfo,
            String helperInfo, String timeHolderInfo, int roomsRepaired, String roomPosition)
    {
        PrintWriter txtWriter = null;
        
        try
        {// overrides the txt file if the name already exits. Otherwise it creates a new file with the name.
            txtWriter = new PrintWriter(new File("assets/maps/saveGame.txt"));
        }
        catch (FileNotFoundException e)
        {
            String msg = "Cannot setup the write file in txtWriter";
            System.out.println(msg + "\n" + e.getMessage());
            return;
        }
        
        txtWriter.print(roomsInfo);
        txtWriter.println();
        
        for (String string : exitInfo)
        {
            txtWriter.print(string);
            txtWriter.println();
        }
        
        txtWriter.print(itemsInfo);
        txtWriter.println();
        
        txtWriter.print(specialItemsInfo);
        txtWriter.println();
        
        txtWriter.print(playerInfo);
        txtWriter.println();
        
        txtWriter.print(saboteurInfo);
        txtWriter.println();
        
        txtWriter.print(helperInfo);
        txtWriter.println();

        txtWriter.print(timeHolderInfo);
        txtWriter.println();

        txtWriter.print("RoomsRepaired: " + roomsRepaired);   // roomsrepaired kommer fra gameinfo.increment 
        txtWriter.println();
        
        txtWriter.print(roomPosition);
        txtWriter.println();
        
        txtWriter.close();
    }
     
    /**
     * This method writes the highscore in a given textfile.
     * @param highScore
     * @param highscoreName 
     */
    @Override
    public void writeHighScore(Map<String, Integer> highScore, String highscoreName)
    {
        // hashmappet må kun være en vis størrelse
        PrintWriter txtWriter = null;
        
        try
        {
           txtWriter = new PrintWriter(new File(highscoreName));
        }
        catch (FileNotFoundException e)
        {
           String msg = "Cannot setup the write file in txtWriter";
           System.out.println(msg + "\n" + e.getMessage());
           return;
        }
        
        for (String key : highScore.keySet())
           txtWriter.println(key + ":" + highScore.get(key));
        
        txtWriter.close();
    }
    
}
