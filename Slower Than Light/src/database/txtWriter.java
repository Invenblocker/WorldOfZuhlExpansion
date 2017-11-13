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
import java.util.LinkedHashMap;
import logic.elements.characters.Helper;
import logic.elements.characters.Item;
import logic.elements.characters.Player;
import logic.elements.characters.Saboteur;
import logic.elements.rooms.ControlRoom;
import logic.elements.rooms.Exit;
import logic.elements.rooms.ItemRoom;
import logic.elements.rooms.Room;
import logic.elements.rooms.WorkshopRoom;
import logic.processors.TimeHolder;

/**
 *
 * @author Loc
 */
public class txtWriter {
    
    public static void saveGame (HashMap<String, Room> rooms, HashMap<String, Item> items, Player player, Saboteur saboteur, 
            Helper helper, int roomsRepaired, TimeHolder time, String saveName)
    {
        Exit exit = null;
        File newSave = new File(saveName);
        PrintWriter txtWriter = null;
        try
        {// overrides the txt file if the name already exits. Otherwise it creates a new file with the name.
            txtWriter = new PrintWriter(newSave);
        }
        catch (FileNotFoundException e)
        {
            String msg = "Cannot setup the write file in txtWriter";
            SystemLog.getErrorLog().writeToLog(msg);
            System.out.println(msg + "\n" + e.getMessage());
            return;
        }
        
        txtWriter.print("Room: ");
      for(String key : rooms.keySet()){
          txtWriter.print(key + " ");
          if(rooms.get(key) instanceof ItemRoom){
              txtWriter.print("ItemRoom ");
              txtWriter.print(rooms.get(key).isOperating());
          }
          else if(rooms.get(key) instanceof ControlRoom){
              txtWriter.print("ControlRoom ");
              txtWriter.print(rooms.get(key).isOperating());
          }
          else if(rooms.get(key) instanceof WorkshopRoom){
              txtWriter.print("WorkshopRoom ");
              txtWriter.print(rooms.get(key).isOperating());
          }
      }
      txtWriter.println();
     
     
     
     
     for(String key : rooms.keySet()){
         txtWriter.print(key + " ");
         if(rooms.get(key).getExit("north") != null){
             txtWriter.print("north ");
             exit = rooms.get(key).getExit(rooms.get(key)); 
             txtWriter.print(exit.isOperating());               // prints the boolean value to the txt file
             txtWriter.print(rooms.get(key).getExit(exit));     // prints the room on the opposing side of the exit to the txt file.
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
     
    txtWriter.print("Item: ");
     for(String key : items.keySet())
     {
     txtWriter.print("Tool: ");
     txtWriter.print(key + " ");
     txtWriter.print(items.get(key).getDefaultRoom().toString() + " ");
     }
     
    txtWriter.println();
      
    txtWriter.print("SpecialItem: ");
     for(String key : items.keySet())
     {
     txtWriter.print("Tool: ");
     txtWriter.print(key + " ");
     }
     
     txtWriter.println();
      
        
     
     txtWriter.print("Player: ");
     txtWriter.print(player.getCurrentRoom().getName() + " "); //players current room name
     txtWriter.print(2);                                       //Inventory size
     
     Item[] itemArray = player.getInventory();              //Item array that holds players current inventory
     int i = player.getItemCount();                         //itemcount of how many items player current holds
     int j = 0;
     while (j < i){
      txtWriter.print(itemArray[j].getName() + " ");       //writes the current items into the txtfile
      txtWriter.print(itemArray[j].getDefaultRoom().getName() + " "); //writes name of current rooms items are stored in
      j++;
     }
     txtWriter.println();
     
    txtWriter.print("Saboteur: ");
    txtWriter.print(saboteur.getCurrentRoom().getName() + " ");
    txtWriter.print(saboteur.DEFAULT_CHANCE_OF_SABOTAGE + " ");
    txtWriter.print(saboteur.CHANCE_OF_SABOTAGE_GROWTH + " ");
    txtWriter.print(saboteur.DEFAULT_CHANCE_OF_SABOTAGE + " ");
    txtWriter.print(saboteur.getChanceOfSabotage() + " ");
    txtWriter.print(saboteur.getStunCountdown());
    txtWriter.println();
    
    txtWriter.print("Helper: ");
    txtWriter.print(helper.getCurrentRoom().getName() + " ");
    txtWriter.print(helper.getHelperTask().name());
    txtWriter.print(helper.getHelperTask());
    txtWriter.print(helper.CHANCE_OF_DISCOVERY_GROWTH);
    txtWriter.print(helper.DEFAULT_CHANCE_OF_DISCOVERY);
    txtWriter.println();
    
    txtWriter.print("RoomsRepaired: " + roomsRepaired);   // roomsrepaired kommer fra gameinfo.increment 
    txtWriter.println();
    
    txtWriter.print("HelperCountDown: " + time.getHelperCountdown());
    txtWriter.println();
    
    txtWriter.print("Time: " + time.getTimeLeft());
    txtWriter.println();
    
    txtWriter.print("OxygenLeft: " + time.getOxygenLeft());
    txtWriter.println();

    txtWriter.close();
     
     
  }
     
   public static void writeHighScore(LinkedHashMap<String, Integer> highScore, String highscoreName) throws FileNotFoundException {
       // hashmappet må kun være en vis størrelse
     File newSave = new File(highscoreName);
     PrintWriter txtWriter = new PrintWriter(highscoreName);
     for (String key : highScore.keySet()){
         txtWriter.println(key + highScore.get(key + " "));
         
     }
     txtWriter.close();
       
   }
    
    
}
