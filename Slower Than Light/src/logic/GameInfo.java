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
    // Reference to TimeHolder's own log
    private final SystemLog ACTION_LOG;
    
    // The allowed percentage of rooms destroyed. If the destroyedRoomsPercentage 
    // is higher than this value, the game is lost
    private final double ALLOWED_ROOMS_DESTROYED_PERCENTAGE = 0.7;
    
    // Information about the destroyed rooms
    private double destroyedRoomsPercentage;
    private List<IRoom> destroyedRooms;
    
    // Reference to an Exit object if any exit has been hacked
    private Exit hackedExit;
    
    // Reference to the helper if the helper has not been killed
    private Helper helper;
    
    // The amount of rooms repaired
    private int roomsRepaired;
    
    // The current highscore in a Map
    private LinkedHashMap<String, Integer> highScoreMap;
    
    // Score of the current game
    private int score;
    
    // Game finished state
    private boolean gameFinished;
    
    /**
     * Constructer which setup a default GameInfo and initializes values
     * @param helper The helper object
     */
    public GameInfo(Helper helper)
    {
        ACTION_LOG = new SystemLog("GameInfo", SystemLog.getActionLog());
        this.helper = helper;
        destroyedRoomsPercentage = 0;
        destroyedRooms = new ArrayList<>();
        roomsRepaired = 0;
        highScoreMap = new LinkedHashMap<>();
        score = 0;
        gameFinished = false;
    }
    
    /**
     * Constructer which setup a default GameInfo and initializes values.
     * This constructor is used for a loaded game
     * @param helper The helper object
     * @param roomsRepaired The amount os rooms repaired
     */
    public GameInfo(Helper helper, int roomsRepaired) 
    {
        this(helper);
        this.roomsRepaired = roomsRepaired;
    }
    
    /**
     * Updates the amount rooms destroyed percentage
     */
    public void updateRoomsDestroyed ()
    {
        Map <String, Room> rooms = Game.getInstance().getRooms();
        destroyedRooms = new ArrayList<>();
        
        for (Room room : rooms.values())
            if (!room.isOperating())
                destroyedRooms.add(room);
        
        updateDestroyedRoomsPercentage();
    }
    
    /**
     * Calculate the score for a game in the current state
     */
    public void calculateHighScore()
    {
        int destroyedRoomsCount = destroyedRooms.size();
        double oxygenLeft = Game.getInstance().getTimeHolder().getOxygenLeft();
        int helperAlivePoints = getHelper() != null? 20 : 0;
        score = (int) ((roomsRepaired * 5) + (oxygenLeft * 5) + helperAlivePoints - (destroyedRoomsCount * 2));
    }
    
    /**
     * Saves the current highscore map with score of the current player
     * @param playerName The name of the current player
     * @return The highcore table as a map
     */
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
    
    /**
     * Updates the percentage of rooms destroyed
     */
    private void updateDestroyedRoomsPercentage ()
    {
        int totalRooms = Game.getInstance().getRooms().size();
        destroyedRoomsPercentage = destroyedRooms.size() / totalRooms;
    }
    
    /**
     * Sort the scores using insertion sort
     * @param highScoreMap Highscore table as a map
     * @return The highscore table sorted and organized in a map
     */
    private Map<String, Integer> sortHighScore (LinkedHashMap<String, Integer>highScoreMap)
    {
        // Sort highscores as list
        List<Map.Entry<String, Integer>> listToSort = new LinkedList<>(highScoreMap.entrySet());
        for (int i = 1; i < listToSort.size(); i++)
        {
            Map.Entry<String, Integer> currentItem = listToSort.get(i);
            int position = i;
            
            while (position > 0 && listToSort.get(position - 1).getValue() > currentItem.getValue())
            {
                Map.Entry<String, Integer> itemToMove = listToSort.get(position - 1);
                listToSort.set(position, itemToMove);
                position--;
            }
            
            listToSort.set(position, currentItem);
        }
        
        // Copy scores from list to Map
        Map<String, Integer>returnHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : listToSort)
            returnHashMap.put(entry.getKey(), entry.getValue());
        
        return returnHashMap;
    }
    
    /**
     * Used for debuging. The message is printed in the console and the log file
     * @param msg Message to log
     */
    private void writeToActionLog(String msg)
    {
        ACTION_LOG.writeToLog(msg);
        System.out.println(msg);
    }
}
