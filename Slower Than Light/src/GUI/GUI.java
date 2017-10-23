/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import logic.elements.characters.Item;
import logic.elements.rooms.Room;

/**
 *
 * @author Erik
 */
public class GUI {
  private MiniMap minimap;  
    
  public GUI() {
this.minimap = new MiniMap();       //Creates minimap object
}
  
  public void updateRoom(Room room) {   
      
  }
  
  public void updateInventory(Item[] inventory) {
      
  }
  
  public void investigate(Room room) {
      
  }
  
  public void printHelp() {
      
  }
  
  public void prinInventory(Item[]inventory) {
      
  }
  
}

