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
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import logic.elements.characters.Item;
import logic.elements.characters.Player;
import logic.elements.characters.Tool;
import logic.elements.rooms.ControlRoom;
import logic.elements.rooms.ItemRoom;
import logic.elements.rooms.Room;
import logic.elements.rooms.WorkshopRoom;


public class txtLoader {
    
    private HashMap<String, Room> rooms;
    private HashMap<String, Item> items;
    private Player player;
 
 
    
public txtLoader(){
    this.rooms = new HashMap<String, Room>();
    this.items = new HashMap<String, Item>();
    
    
}

/**
 * Takes the name of a txt file containing rooms and their exits, and items and their room.
 * Then puts the rooms into the rooms HashMap with their exits, and puts the items into the items HashMap, with their respective rooms.
 * @param gameName
 * @throws FileNotFoundException 
 */

public void loadGame (String gameName) throws FileNotFoundException { 
    Scanner sc = new Scanner(new File(gameName));
    while(sc.hasNext()){
        String line = sc.nextLine();  
        String[] words = line.split(" ");
        if(words[0].equals("Room:")){      // adds all rooms to the hashmap rooms.
            roomToHashMap(words);
        }
        else if(words[0].equals("Item:")){
            itemToHashMap(words);
        }
        else if(words[0].equals("Player:")){
            loadPlayer(words);
        }
        else{
            addRoomExits(words);
        }
    }
    
    addRoomRepairTools();
 }


private void loadPlayer(String[] words){
    Room room = null;
    for(String key : rooms.keySet()){
        if(words[1].equals(key)){
            room = rooms.get(key);
        }
    }
    this.player = new Player(room, Integer.parseInt(words[2]));
    
}

private void addRoomRepairTools(){
    Room room = null;
    Item item = null;
    List keys = new ArrayList(items.keySet());
    keys.remove("ducttape");
    Collections.shuffle(keys);                 // shuffler keys
    
    for (String key : rooms.keySet()){
            if(rooms.get(key) instanceof ItemRoom ){
              room = rooms.get(key);  
                for (Object ob : keys){   
                item = items.get(ob);
                }
               room.setRepairTools((Tool) item);
               keys.remove(item.getName());
            }
        }

    
}

  
private void addRoomExits(String[] words){
    int i = 1;
    int j = 2;
    Room room = null;
    Room room2 = null;
    while (j < words.length){                // tjekker at vi ikke overskrider arrayet
        for (String key : rooms.keySet()){   // tjekker alle keys i vores hashmap rooms (workshop, controlroom, pub, lab)
            if (key.equals(words[0])){       // tjekker om plads 0 (et rum) er det rum vi kigger på.
                room = rooms.get(key);       // sætter rum = det rum vi vil arbejde med.
            }   
        }
        for (String key : rooms.keySet()){   // tjekker alle rum i hashmappet room
            if (key.equals(words[j])){       // finder det andet rum som skal sættes som exit til room.
                room2=rooms.get(key);        // sætter rummet til room2.
                room.setExit(words[i], room2); // sætter exit med plads i (en direction) og et room.
            }
        }
        i = i + 2;
        j = j + 2;
    }
    
  }
  
private void itemToHashMap(String[] words){
    int x = 1;
    int i = 2;
    int j = 3;
    while (j < words.length){
        if(words[i].equals("ducttape") && words[j].equals("null")){
            items.put(words[i], new Tool(words[i], null));
        }
        for (String key : rooms.keySet()){
            if (key.equals(words[j])){
              if(words[x].equals("Tool")){ 
                items.put(words[i], new Tool(words[i],rooms.get(key)));
                    
                
             }
            }  
        }
    x += 3;    
    i += 3;
    j += 3;
        
    }
    for (String key : items.keySet()){
        System.out.println(key);
    }
}


//Method inserts room to our hasmap rooms
//Private as the method is only used and accessed in the txtLoader class
private void roomToHashMap(String[] words) {
        
     int i = 1;                 //index for room in our txt file            
     int j = 2;                 //index for boolean in our txt file    
    while (j < words.length) {                                                       //As long j is less than array lenght put room
        
        if (words[j].equals("ItemRoom")){
        rooms.put(words[i], new ItemRoom(words[i]));   
        }
        else if(words[j].equals("WorkshopRoom")){
        rooms.put(words[i], new WorkshopRoom(words[i]));         
        }
        else{
        rooms.put(words[i], new ControlRoom(words[i]));        
        }
        i += 2;                                                                     //Jumps to room index in our txt
        j += 2;                                                                     //jumps to next boolean in txt
                    

        } 
    
}
 

public HashMap<String, Room> getRooms()
{
    return this.rooms;
}

public HashMap<String, Item> getItems()
{
    return this.items;
}




    
}