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
import logic.elements.characters.Helper;
import logic.elements.characters.Item;
import logic.elements.characters.Player;
import logic.elements.characters.Saboteur;
import logic.elements.rooms.ControlRoom;
import logic.elements.rooms.Exit;
import logic.elements.rooms.ItemRoom;
import logic.elements.rooms.Room;
import logic.elements.rooms.WorkshopRoom;

/**
 *
 * @author Loc
 */
public class txtWriter {
    
  public void saveGame (HashMap<String, Room> rooms, HashMap<String, Item> items, Player player, Saboteur saboteur, 
            Helper helper, int roomsRepaired, String saveName) throws FileNotFoundException
  {
     Exit exit = null;
     File newSave = new File(saveName);
     PrintWriter txtWriter = new PrintWriter(newSave);   // overrides the txt file if the name already exits. Otherwise it creates a new file with the name.
     txtWriter.print("Room: ");
      for(String key : rooms.keySet()){
          txtWriter.print(key + " ");
          if(rooms.get(key) instanceof ItemRoom){
              txtWriter.print("ItemRoom ");
          }
          else if(rooms.get(key) instanceof ControlRoom){
              txtWriter.print("ControlRoom ");
          }
          else if(rooms.get(key) instanceof WorkshopRoom){
              txtWriter.print("WorkshopRoom ");
          }
      }
      txtWriter.println();
     
     
     
     
     for(String key : rooms.keySet()){
         txtWriter.print(key + " ");
         if(rooms.get(key).getExit("north") != null){
             txtWriter.print("north ");
             exit = rooms.get(key).getExit(rooms.get(key));
             txtWriter.print(exit.isOperating());
             txtWriter.print(rooms.get(key).getExit(exit));
         } 
         if(rooms.get(key).getExit("south") != null) {
             txtWriter.print("south ");
             exit = rooms.get(key).getExit(rooms.get(key));
             txtWriter.print(exit.isOperating());
             txtWriter.print(rooms.get(key).getExit(exit));
         }
         if(rooms.get(key).getExit("west") != null) {
             txtWriter.print("west ");
             exit = rooms.get(key).getExit(rooms.get(key));
             txtWriter.print(exit.isOperating());
             txtWriter.print(rooms.get(key).getExit(exit));
         }
         if(rooms.get(key).getExit("east") != null) {
             txtWriter.print("east "); // direction
             exit = rooms.get(key).getExit(rooms.get(key)); 
             txtWriter.print(exit.isOperating()); // isOperating
             txtWriter.print(rooms.get(key).getExit(exit)); // Room connected
         }
         txtWriter.println();
         
     }
     
      
        
  }
     
   public void writeHighScore(HashMap<String, Integer> highScore) {
       // hashmappet må kun være en vis størrelse
   }
    
    
}
