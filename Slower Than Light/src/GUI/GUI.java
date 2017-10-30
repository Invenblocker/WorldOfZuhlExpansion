/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import logic.Game;
import logic.elements.characters.Item;
import logic.elements.rooms.Room;

/**
 *
 * @author Erik
 */
public class GUI {
  private MiniMap minimap;  
    
  public GUI() {
this.minimap = new MiniMap();       //Creates new minimap object
}
  
  public void updateMinimap(Room saboteurRoom, Room[] destroyedRooms) { //updates saboteur position, calls update in MiniMap class.
      minimap.update(saboteurRoom, destroyedRooms);
      
      
  }
  
  public void updateRoom(Room room) {    //updates player position to minimap   
      minimap.updatePlayerPosition(room);
      
      
  }
  
  public void updateInventory(Item[] inventory) {
     System.out.println(inventory);
      
  }
  
  public void investigate(Room room) {
     
      
  }
    
  public void printHelp() {
    System.out.println("YOU BE FUCKED");
    System.out.println();
    System.out.println("Your command words are:");
    Game.getInstance().getParser().showCommands();
      
  }
  
  public void prinInventory(Item[]inventory) {
      System.out.println(inventory);
      
  }
  
}

