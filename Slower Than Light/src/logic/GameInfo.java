/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

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
    
    private boolean gameFinished;
    
    public GameInfo()
    {
        roomsDestroyedPercentage = 0;
        gameFinished = false;
    }
    
    public double getALLOWED_ROOMS_DESTROYED_PERCENTAGE() {return ALLOWED_ROOMS_DESTROYED_PERCENTAGE;}

    public double getRoomsDestroyedPercentage() {return roomsDestroyedPercentage;}
    /*public void setRoomsDestroyedPercentage(double roomsDestroyedPercentage) {
        this.roomsDestroyedPercentage = roomsDestroyedPercentage;
    }*/
    
    public boolean isGameFinished () {return gameFinished;}
    public void setGameFinished(boolean value)
    {
        gameFinished = value;
    }
    
    public void updateRoomsDestroyed ()
    {
        
    }
    
    private void updateRoomsDestroyedPercentage ()
    {
        
        HashMap <String, Room> rooms = Game.getInstance().getRooms();

        int destroyedRooms = 0;
        int totalRooms = rooms.size();
          
        for (Map.Entry<String, Room> entry : rooms.entrySet()) {
            String key = entry.getKey();
            Room room = entry.getValue();
            if (!room.isOperating()) {
                destroyedRooms++;
            }
        }
        
        double destroyedRoomsPercentage = destroyedRooms / totalRooms;
        //gameInfo.setRoomsDestroyedPercentage(destroyedRoomsPercentage);
        roomsDestroyedPercentage = destroyedRoomsPercentage;
    }
}
