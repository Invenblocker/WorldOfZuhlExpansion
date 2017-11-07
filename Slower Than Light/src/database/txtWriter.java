/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import logic.elements.characters.Item;
import logic.elements.characters.Player;
import logic.elements.characters.Saboteur;
import logic.elements.rooms.Room;

/**
 *
 * @author Loc
 */
public class txtWriter {
    
  public void saveGame (HashMap<String, Room> rooms, HashMap<String, Item> items, Player player, Saboteur saboteur, 
            Helper helper, int roomsRepaired, String saveName) throws FileNotFoundException
  {
     
     File newSave = new File(saveName);
     PrintWriter txtWriter = new PrintWriter(newSave);   // overrides the txt file if the name already exits. Otherwise it creates a new file with the name.
     txtWriter.print("Room: ");
     for(String key : rooms.keySet()){
         txtWriter.print(key);
         
     }
     
      
        
  }
     
   public void writeHighScore(HashMap<String, Integer> highScore) {
       
   }
    
    
}
