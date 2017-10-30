/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import logic.elements.rooms.Room;

/**
 *
 * @author Erik
 */
public class GameInfo {
    
    private final double ALLOWED_ROOMS_DESTROYED_PERCENTAGE = 0.7;
    private double destroyedRoomsPercentage;
    private ArrayList<Room> destroyedRooms;
    
    private boolean gameFinished;
    
    public GameInfo()
    {
        destroyedRoomsPercentage = 0;
        destroyedRooms = new ArrayList<>();
        gameFinished = false;
    }
    
    public void updateRoomsDestroyed ()
    {
        HashMap <String, Room> rooms = Game.getInstance().getRooms();
        destroyedRooms = new ArrayList<>();
        
        for (Room room : rooms.values())
            if (!room.isOperating())
                destroyedRooms.add(room);
        
        updateDestroyedRoomsPercentage();
    }
    
    public double getALLOWED_ROOMS_DESTROYED_PERCENTAGE() {return ALLOWED_ROOMS_DESTROYED_PERCENTAGE;}

    public double getDestroyedRoomsPercentage() {return destroyedRoomsPercentage;}

    public Room[] getDestroyedRooms() {return destroyedRooms.toArray(new Room[0]);}
    
    public boolean isGameFinished () {return gameFinished;}
    public void setGameFinished(boolean value)
    {
        gameFinished = value;
    }
    
    private void updateDestroyedRoomsPercentage ()
    {
        int totalRooms = Game.getInstance().getRooms().size();
        destroyedRoomsPercentage = destroyedRooms.size() / totalRooms;
    }
}
