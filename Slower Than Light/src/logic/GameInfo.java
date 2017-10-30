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
    private double roomsDestroyedPercentage;
    private ArrayList<Room> destroyedRooms;
    
    private boolean gameFinished;
    
    public GameInfo()
    {
        roomsDestroyedPercentage = 0;
        destroyedRooms = new ArrayList<>();
        gameFinished = false;
    }
    
    public void updateRoomsDestroyed ()
    {
        HashMap <String, Room> rooms = Game.getInstance().getRooms();
        destroyedRooms = new ArrayList<>();
        
        for (Room room : rooms.values())
            if (room.isOperating())
                destroyedRooms.add(room);
        
        updateRoomsDestroyedPercentage();
    }
    
    public double getALLOWED_ROOMS_DESTROYED_PERCENTAGE() {return ALLOWED_ROOMS_DESTROYED_PERCENTAGE;}

    public double getRoomsDestroyedPercentage() {return roomsDestroyedPercentage;}
    
    public boolean isGameFinished () {return gameFinished;}
    public void setGameFinished(boolean value)
    {
        gameFinished = value;
    }
    
    private void updateRoomsDestroyedPercentage ()
    {
        int totalRooms = Game.getInstance().getRooms().size();
        roomsDestroyedPercentage = destroyedRooms.size() / totalRooms;
    }
}
