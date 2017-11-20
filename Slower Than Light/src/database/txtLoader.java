package database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.Game;
import logic.GameInfo;
import logic.elements.characters.Helper;
import logic.elements.characters.HelperTask;
import logic.elements.characters.Item;
import logic.elements.characters.Player;
import logic.elements.characters.Saboteur;
import logic.elements.characters.Tool;
import logic.elements.rooms.ControlRoom;
import logic.elements.rooms.Exit;
import logic.elements.rooms.ItemRoom;
import logic.elements.rooms.Room;
import logic.elements.rooms.WorkshopRoom;
import logic.processors.TimeHolder;


public class txtLoader
{
    
    private HashMap<String, Room> rooms;
    private HashMap<String, Item> items;
    private HashMap<String, Item> specialItems;
    private LinkedHashMap<String, Integer> highScore;
    private Player player;
    private Saboteur saboteur;
    private Helper helper;
    private TimeHolder timeHolder;
    private String gameName;
    
    public txtLoader()
    {
        this.gameName = gameName;
        this.rooms = new HashMap<String, Room>();
        this.items = new HashMap<String, Item>();
        this.specialItems = new HashMap<String, Item>();
        this.player = new Player(null, 0);
        this.saboteur = new Saboteur(null, 0, 0, 0);
        this.helper = new Helper(null, null, 0, 0);
        this.timeHolder = new TimeHolder();
    }

    /**
     * Takes the name of a txt file containing rooms and their exits, and items and their room.
     * Then puts the rooms into the rooms HashMap with their exits, and puts the items into the items HashMap, with their respective rooms.
     * @param gameName
     * @throws FileNotFoundException 
     */
    
    public void newGame(String gameName) 
    {
        initializeGame(gameName);
        
    }
    
    public void loadGame(String gameName)
    {
      initializeGame(gameName);  
    }
    
    
    public void initializeGame (String gameName)
    {
        Scanner sc = null;
        
        try {
            sc = new Scanner(new File(gameName));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(txtLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(sc.hasNext())
        {
            String line = sc.nextLine();  
            String[] words = line.split(" ");
            if(words[0].equals("Room:")){      // adds all rooms to the hashmap rooms.
                roomToHashMap(words);
            }
            else if(words[0].equals("Item:")){
                itemToHashMap(words);
            }
            else if(words[0].equals("Player:")){
                initializePlayer(words);     // mangler noget, special item og item? defaultRoom
            }
            else if(words[0].equals("Saboteur:")){
                initializeSaboteur(words);
            }
            else if(words[0].equals("Helper:")) {
                initializeHelper(words); 
            }
            else if(words[0].equals("SpecialItem:")){
                specialItemToHashMap(words);
            }
            else if(words[0].equals("RoomsRepaired: ")){
                getRoomsRepaired(Integer.parseInt(words[1]));
                /*
                int i = Integer.parseInt(words[1]);
                while(i > 0){
                Game.getInstance().getGameInfo().incrementRoomsRepaired();
                    System.out.println(i);
                i--;
                }
                */
            }
            else if(words[0].equals("TimeHolder:")){
                this.timeHolder = new TimeHolder(Double.parseDouble(words[1]), Double.parseDouble(words[2]));
                this.timeHolder.setHelperCountdown(Integer.parseInt(words[3]));
                this.timeHolder.setSaboteurCountdown(Integer.parseInt(words[4]));
            }
            else{
                addRoomExits(words);
            }
        }
    }
    
    public int getRoomsRepaired(int i){
       return i; 
    }
    
    
     public HashMap<String, Room> getRooms()
    {
        return this.rooms;
    }
    
    public HashMap<String, Item> getItems()
    {
        return this.items;
    }
    
    
    public HashMap<String, Item> getSpecialItems() {
        return this.specialItems;
    }
    
    
    
    public Player getPlayer() {
        return player;
    }
    
    public LinkedHashMap<String, Integer> getHighscore() throws FileNotFoundException { 
        this.highScore = new LinkedHashMap<String, Integer> ();
        String name;
        int score;
        Scanner sc = new Scanner (new File("highScore"));
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] words = line.split (" ");
            
            name = words[0];
            score = Integer.parseInt(words[1]);
            highScore.put(name, score);
            
         
            
        }
        
        return  highScore;
        
        
    }
    
    
    
     //Method inserts room to our hasmap rooms
    //Private as the method is only used and accessed in the txtLoader class
    private void roomToHashMap(String[] words)
    {
        int i = 1;                 //index for room in our txt file            
        int j = 2;                 //index for boolean in our txt file    
        int k = 3;
        Room room;
        while (j < words.length) {                                                       //As long j is less than array lenght put room

            if (words[j].equals("ItemRoom")){
                rooms.put(words[i], new ItemRoom(words[i])); 
                room = rooms.get(words[i]);
                room.setOperating(Boolean.parseBoolean(words[k]));
            }
            else if(words[j].equals("WorkshopRoom")){
                rooms.put(words[i], new WorkshopRoom(words[i])); 
                room = rooms.get(words[i]);
                room.setOperating(Boolean.parseBoolean(words[k]));
            }
            else{
                rooms.put(words[i], new ControlRoom(words[i])); 
                room = rooms.get(words[i]);
                room.setOperating(Boolean.parseBoolean(words[k]));
            }
            i += 3;                                                                     //Jumps to room index in our txt
            j += 3;                                                                     //jumps to next boolean in txt
            k += 3;
        }
    }
    
      private void itemToHashMap(String[] words){
        int i = 1;
        int j = 2;
        int k = 3;
        
        while (k < words.length)
        {
            for (String key : rooms.keySet())
                if (key.equals(words[k]))
                    if(words[i].equals("Tool"))
                        items.put(words[j], new Tool(words[j], (ItemRoom) rooms.get(key)));
            
            i += 3;    
            j += 3;
            k += 3;
        }
    }
      
      
    private void specialItemToHashMap(String[] words)   //Adds specialItem to HashMap specialItems
    {
    int i = 1;      //index for specialItems in txtfile
    int j = 2;
        
    while (j < words.length){        //As long as i is less then the length of do ->  speicialItems.put
        if (words[i].equals("Tool")){
            System.out.println("trying specialitem with: " + words[j]);
            specialItems.put(words[j],new Tool(words[j]));
            
        } else if (words[i].equals("Item")){
            specialItems.put(words[j],new Item(words[j])); 
            
        }
        i += 2;
        j += 2;
    }
       
    }
      
    private void initializeSaboteur(String[] words){
    Room room;
    room = rooms.get(words[1]);
    this.saboteur = new Saboteur(room, Double.parseDouble(words[2]), Double.parseDouble(words[3]), Double.parseDouble(words[4]));
    this.saboteur.setChanceOfSabotage(Double.parseDouble(words[5]));
    this.saboteur.addStunCountdown(Integer.parseInt(words[6]));
    
    }
    
    private void initializeHelper(String[] words){
    Room room;
    room = rooms.get(words[1]);     //Helpers room in txt file
    this.helper = new Helper(room, words[2], Double.parseDouble(words[4]), Double.parseDouble(words[5])); //[3]chance of discovery growht og [4] er
    helper.setTask(HelperTask.getHelperTask(words[3]));
   
        
    }
            
    
      
    private void initializePlayer(String[] words)
    {
     Room room;                     //Create reference to room
     room = rooms.get(words[1]);    //Sets room reference equal to index 1 in our hashmap, which is a room.
     
     this.player= new Player(room, Integer.parseInt(words[2]));
     int i = 3;         //index for item in txtfile
   
     
     if(items.containsKey(i))           //checks if txtfile contains same key in items hashmap
     {
      this.player.addItem(items.get(words[i])); //adds item to player inventory
     }
     else if (specialItems.containsKey(i))  //checks if txtfile contains same key in specialItems hashmap
     {
         this.player.addItem(specialItems.get(words[i]));    //adds specialItem to player inventory
        
     }
     i++;
    
        }
    
    
    
    private void addRoomExits(String[] words){
        int i = 1;
        int j = 2;
        int k = 3;
        Room room = null;
        Room room2 = null;
        Exit exit = null;
        
        while (k < words.length)
        {                                           // tjekker at vi ikke overskrider arrayet
            for (String key : rooms.keySet())       // tjekker alle keys i vores hashmap rooms (workshop, controlroom, pub, lab)
                if (key.equals(words[0]))           // tjekker om plads 0 (et rum) er det rum vi kigger på.
                    room = rooms.get(key);          // sætter rum = det rum vi vil arbejde med.
            
            for (String key : rooms.keySet()){      // tjekker alle rum i hashmappet room
                if (key.equals(words[k])){          // finder det andet rum som skal sættes som exit til room.
                 room2 = rooms.get(key);           // sætter rummet til room2.
                 exit = new Exit(room, room2);
                 exit.setOperating(Boolean.parseBoolean(words[j]));
                 room.setExit(words[i], exit);  // sætter exit med plads i (en direction) og et room.
                }
            }
            i += 3;
            j += 3;
            k += 3;
        }
    }

    public Saboteur getSaboteur() {
        return saboteur;
    }

    public Helper getHelper() {
        return helper;
    }

    public TimeHolder getTimeHolder() {
        return timeHolder;
    }
    
    
}