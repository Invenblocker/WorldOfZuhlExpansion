/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import database.txtWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import logic.elements.rooms.Exit;
import logic.elements.rooms.Room;
import sun.security.krb5.internal.KDCOptions;

/**
 *
 * @author Erik
 */
public class GameInfo {
    
    private final double ALLOWED_ROOMS_DESTROYED_PERCENTAGE = 0.7;
    private double destroyedRoomsPercentage;
    private ArrayList<Room> destroyedRooms;
    private Exit hackedExit;
    private int roomsRepaired;
    private int highScore;
    private boolean gameFinished;
    
    public GameInfo()
    {
        destroyedRoomsPercentage = 0;
        destroyedRooms = new ArrayList<>();
        gameFinished = false;
        roomsRepaired = 0;
        highScore = 0;
    }
    
    public GameInfo(int roomsRepaired) 
    {
        this();
        this.roomsRepaired = roomsRepaired;
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
    
    public void calculateHighScore()
    {
        int destroyedRoomsCount = destroyedRooms.size();
        double oxygenLeft = Game.getInstance().getTimeHolder().getOxygenLeft();
        highScore = (int) ((roomsRepaired * 5) + (oxygenLeft * 5) - (destroyedRoomsCount * 2)); 
    }
    
    public LinkedHashMap<String, Integer> saveHighScore(String name) 
    {
        LinkedHashMap<String, Integer> highScoreHashMap = Game.getInstance().getHighScore();
        highScoreHashMap.put(name, highScore);
        sortHighScore(highScoreHashMap);
        txtWriter.writeHighScore(highScoreHashMap);
        
        return highScoreHashMap;
    }
    
    public double getALLOWED_ROOMS_DESTROYED_PERCENTAGE() {return ALLOWED_ROOMS_DESTROYED_PERCENTAGE;}

    public double getDestroyedRoomsPercentage() {return destroyedRoomsPercentage;}

    public Room[] getDestroyedRooms() {return destroyedRooms.toArray(new Room[0]);}
    
    public void incrementRoomsRepaired() 
    {
        roomsRepaired++;
    }
    
    public Exit getHackedExit() 
    {
        return hackedExit;
    }
    public void setHackedExit(Exit value)
    {
        hackedExit = value;
    }
    
    public void repairHackedExit() 
    {
        hackedExit = null;
    }
    
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
    
    private LinkedHashMap<String, Integer>sortHighScore (LinkedHashMap<String, Integer>highScoreMap)
    {
        List<Map.Entry<String, Integer>>listToSort = new LinkedList<Map.Entry<String, Integer>>(highScoreMap.entrySet());
        
        Map.Entry<String, Integer> temp;
        for (int i = 1; i < listToSort.size(); i++) 
        {
            for (int j = i; j > 0; j--) 
            {
                if (listToSort.get(j).getValue() > listToSort.get(j-1).getValue())
                {
                    temp = listToSort.get(j);
                    listToSort.set(j,listToSort.get(j-1));
                    listToSort.set(j-1,temp);
                    
                }
                
            }
        }
        LinkedHashMap<String, Integer>returnHashMap = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : listToSort) 
            {
                returnHashMap.put(entry.getKey(), entry.getValue());
            }
        
        return returnHashMap;
    }
    
}
