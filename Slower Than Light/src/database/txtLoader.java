package database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import acq.ILoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class txtLoader implements ILoader
{
    private String[] roomsInfo;
    private String[] itemsInfo;
    private String[] specialItemsInfo;
    private List<String[]> exitInfo;
    private String[] playerInfo;
    private String[] saboteurInfo;
    private String[] helperInfo;
    private String[] timeHolderInfo;
    
    private LinkedHashMap<String, Integer> highScore;
    private int roomsRepaired;
    
    public txtLoader()
    {
        this.highScore = new LinkedHashMap<>();
        this.roomsRepaired = 0;
    }

    /**
     * Takes the name of a txt file containing rooms and their exits, and items and their room.
     * Then puts the rooms into the rooms HashMap with their exits, and puts the items into the items HashMap, with their respective rooms.
     * @param gameName 
     */
    @Override
    public void newGame(String gameName) 
    {
        exitInfo = new ArrayList<>();
        initializeGame(gameName);
    }
    
    @Override
    public void loadGame(String gameName)
    {
        exitInfo = new ArrayList<>();
        initializeGame(gameName);  
    }
    
    public void initializeGame (String gameName)
    {
        Scanner sc = null;
        
        try
        {
            sc = new Scanner(new File(gameName));
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(txtLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(sc.hasNext())
        {
            String line = sc.nextLine();  
            String[] words = line.split(" ");
            if(words[0].equals("Room:")){      // adds all rooms to the hashmap rooms.
                roomsInfo = words;
            }
            else if(words[0].equals("Item:")){
                itemsInfo = words;
            }
            else if(words[0].equals("Player:")){// mangler noget, special item og item? defaultRoom
                playerInfo = words;
            }
            else if(words[0].equals("Saboteur:")){
                saboteurInfo = words;
            }
            else if(words[0].equals("Helper:")) {
                helperInfo = words;
            }
            else if(words[0].equals("SpecialItem:")){
                specialItemsInfo = words;
            }
            else if(words[0].equals("RoomsRepaired:")){
                roomsRepaired = Integer.parseInt(words[1]);
            }
            else if(words[0].equals("TimeHolder:")){
                timeHolderInfo = words;
            }
            else{
                exitInfo.add(words);
            }
        }
    }
    
    @Override
    public String[] getRoomsInfo() {return roomsInfo;}
    
    @Override
    public String[] getItemsInfo() {return itemsInfo;}
    
    @Override
    public String[] getSpecialItemsInfo() {return specialItemsInfo;}
    
    @Override
    public List<String[]> getExitInfo() {return exitInfo;}

    @Override
    public String[] getPlayerInfo() {return playerInfo;}

    @Override
    public String[] getSaboteurInfo() {return saboteurInfo;}

    @Override
    public String[] getHelperInfo() {return helperInfo;}

    @Override
    public String[] getTimeHolderInfo() {return timeHolderInfo;}
    
    @Override
    public LinkedHashMap<String, Integer> getHighscore() { 
        this.highScore = new LinkedHashMap<String, Integer> ();
        String name;
        int score;
        Scanner sc = null;
        try {
            sc = new Scanner (new File("highScore"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(txtLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] words = line.split (" ");
            
            name = words[0];
            score = Integer.parseInt(words[1]);
            highScore.put(name, score);
        }
        
        return  highScore;
    }
    
    @Override
    public int getRoomsRepaired ()
    {
        return roomsRepaired;
    }
    
}