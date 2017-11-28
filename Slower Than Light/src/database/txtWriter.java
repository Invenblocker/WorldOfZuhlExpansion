/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import acq.IWriter;
import java.awt.Point;
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

    @Override
    public void saveGame(String roomsInfo, String itemsInfo,String specialItemsInfo,
            List<String> exitInfo, String playerInfo, String saboteurInfo,
            String helperInfo, String timeHolderInfo, int roomsRepaired, String roomPosition)
    {
        File newSave = new File("assets/maps/saveGame.txt");
        PrintWriter txtWriter = null;
        try
        {// overrides the txt file if the name already exits. Otherwise it creates a new file with the name.
            txtWriter = new PrintWriter(newSave);
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
     
    @Override
    public void writeHighScore(Map<String, Integer> highScore, String highscoreName)
    {
         // hashmappet må kun være en vis størrelse
         File newSave = new File(highscoreName);
         PrintWriter txtWriter = null;

         try
         {
             txtWriter = new PrintWriter(highscoreName);
         }
         catch (FileNotFoundException e)
         {
             String msg = "Cannot setup the write file in txtWriter";
             System.out.println(msg + "\n" + e.getMessage());
             return;
         }


         for (String key : highScore.keySet())
         {
             txtWriter.println(key + highScore.get(key + " "));
         }

         txtWriter.close();
    }
    
}
