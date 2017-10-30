/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.ArrayList;
import logic.Game;
import logic.elements.rooms.Room;

/**
 *
 * @author Peter
 */
public class MiniMap {
   private ArrayList<Room> DestroyedRoomsInMiniMap;  //Creates arrayList that stores destroyed rooms  
   public MiniMap(){
       
   }
 
public void update(Room saboteurRoom, Room[] destroyedRoomsIngame){                               
    System.out.print("Updated saboteur position: ");      //updates saboteur position to minimap
    System.out.println(saboteurRoom); 
    updateDestroyedRooms(destroyedRoomsIngame);           //calls method, updates destroyedrooms in Game
    System.out.println("Destroyed rooms in game: ");
}
public void updatePlayerPosition(Room room){
    System.out.print("Current player position: ");        //updates player position to minimap
    System.out.println(Game.getInstance().getPlayer().getCurrentRoom());
}

private void updateDestroyedRooms(Room[] destroyedRoomsInGame) {   //Sets param destroydRoomsInGame
    for (Room destroyedRoom : destroyedRoomsInGame) 
        if(!DestroyedRoomsInMiniMap.contains(destroyedRoom)) {      //only unique elements, only add if !not already
            DestroyedRoomsInMiniMap.add(destroyedRoom);             //contains room DestroyedRoomsInMiniMap 
            
            
   }
    

    
}    
    
    
            
        
    
    
    
    

        

    
}
