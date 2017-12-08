/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import acq.IGameInfo;
import acq.IRoom;
import database.txtWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import logic.elements.characters.Helper;
import logic.elements.rooms.Exit;
import logic.elements.rooms.Room;

/**
 *
 * @author Erik
 */
public class GameInfo implements IGameInfo
{
    private final SystemLog ACTION_LOG;
    
    private final double ALLOWED_ROOMS_DESTROYED_PERCENTAGE = 0.7;
    private double destroyedRoomsPercentage;
    private List<IRoom> destroyedRooms;
    
    private Exit hackedExit;
    private Helper helper;
    private int roomsRepaired;
    private LinkedHashMap<String, Integer> highScoreMap;
    private int score;
    private boolean gameFinished;
    
    public GameInfo(Helper helper)
    {
        ACTION_LOG = new SystemLog("GameInfo", SystemLog.getActionLog());
        this.helper = helper;
        destroyedRoomsPercentage = 0;
        destroyedRooms = new ArrayList<>();
        roomsRepaired = 0;
        score = 0;
        gameFinished = false;
    }
    
    public GameInfo(Helper helper, int roomsRepaired) 
    {
        this(helper);
        this.roomsRepaired = roomsRepaired;
    }
    
    public void updateRoomsDestroyed ()
    {
        Map <String, Room> rooms = Game.getInstance().getRooms();
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
        int helperAlivePoints = getHelper() != null? 20 : 0;
        score = (int) ((roomsRepaired * 5) + (oxygenLeft * 5) - (destroyedRoomsCount * 2) + helperAlivePoints); 
    }
    
    @Override
    public Map<String, Integer> saveHighScore(String playerName)
    {
        highScoreMap.put(playerName, score);
        sortHighScore(highScoreMap);
        txtWriter Writer = new txtWriter();
        Writer.writeHighScore(highScoreMap, playerName);
        
        return highScoreMap;
    }
    
    public double getALLOWED_ROOMS_DESTROYED_PERCENTAGE() {return ALLOWED_ROOMS_DESTROYED_PERCENTAGE;}

    public double getDestroyedRoomsPercentage() {return destroyedRoomsPercentage;}

    @Override
    public List<IRoom> getDestroyedRooms() {return destroyedRooms;}
    
    public int getRoomsRepaired () {return roomsRepaired;}
    public void incrementRoomsRepaired() 
    {
        roomsRepaired++;
    }
    
    public Exit getHackedExit() {return hackedExit;}
    public void setHackedExit(Exit value)
    {
        if(value == hackedExit)
            return;
        
        hackedExit = value;
    }
    
    public void repairHackedExit() 
    {
        hackedExit = null;
    }
    
    public Helper getHelper() {return helper;}
    public void killHepler()
    {
        writeToActionLog("Helper(" + helper.getName() + ") was killed");
        helper = null;
    }

    @Override
    public Map<String, Integer> getHighScoreMap () {return highScoreMap;}
    
    @Override
    public int getScore () {return score;}
    
    @Override
    public boolean isGameFinished () {return gameFinished;}
    public void setGameFinished(boolean value)
    {
        if(value == gameFinished)
            return;
        
        gameFinished = value;
        writeToActionLog("Game finished set to " + value);
    }
    
    private void updateDestroyedRoomsPercentage ()
    {
        int totalRooms = Game.getInstance().getRooms().size();
        destroyedRoomsPercentage = destroyedRooms.size() / totalRooms;
    }
    
    private Map<String, Integer>sortHighScore (LinkedHashMap<String, Integer>highScoreMap)
    {
        List<Map.Entry<String, Integer>>listToSort = new LinkedList<>(highScoreMap.entrySet());
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
        
        Map<String, Integer>returnHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : listToSort)
            returnHashMap.put(entry.getKey(), entry.getValue());
        
        return returnHashMap;
    }
    
    private void writeToActionLog(String msg)
    {
        ACTION_LOG.writeToLog(msg);
        System.out.println(msg);
    }
}
