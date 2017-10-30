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
public class MiniMap
{
    private ArrayList<Room> destroyedRoomsInMiniMap;  //Creates arrayList that stores destroyed rooms  
    
    public MiniMap()
    {
        destroyedRoomsInMiniMap = new ArrayList<>();
    }
 
    public void update(Room saboteurRoom, Room[] destroyedRoomsIngame){                               
        System.out.print("Updated saboteur position: ");      //updates saboteur position to minimap
        System.out.println(saboteurRoom.getName()); 
        updateDestroyedRooms(destroyedRoomsIngame);           //calls method, updates destroyedrooms in Game
        System.out.println("Destroyed rooms in game: " + roomArrayToString());
    }
    
    public void updatePlayerPosition(Room playerRoom){
        System.out.print("Current player position: ");        //updates player position to minimap
        System.out.println(playerRoom.getName());
    }

    private void updateDestroyedRooms(Room[] destroyedRoomsInGame) {    //Sets param destroydRoomsInGame
        for (Room destroyedRoom : destroyedRoomsInGame) 
            if(!destroyedRoomsInMiniMap.contains(destroyedRoom))        //only unique elements, only add if !not already
                destroyedRoomsInMiniMap.add(destroyedRoom);             //contains room DestroyedRoomsInMiniMap 
    }    

    private String roomArrayToString ()
    {
        if (destroyedRoomsInMiniMap.isEmpty())
            return "";
        
        String roomsAsString = "";

        for (Room room : destroyedRoomsInMiniMap)
            roomsAsString = roomsAsString + room.getName() + ", ";

        roomsAsString = roomsAsString.substring(0, roomsAsString.length() - 2);

        return roomsAsString;
        
    }
    
}
