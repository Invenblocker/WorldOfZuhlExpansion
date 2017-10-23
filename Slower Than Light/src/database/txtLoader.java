package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import logic.elements.characters.Item;
import logic.elements.rooms.Room;



public class txtLoader {
    
    private HashMap<String, Room> rooms;
    private HashMap<String, Item> items;
 
 
    
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
            roomToHash(words);
        }
        else if(words[0].equals("Item:")){
            itemToHash(words);
        }
        else{
            addRoomExits(words);
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
    for (String key : rooms.keySet()){
          System.out.println( key+" "+rooms.get(key).getExits());
      }
  }
  
private void itemToHash(String[] words){
    int i = 1;
    int j = 2;
    while (j < words.length){
        for (String key : rooms.keySet()){
            if (key.equals(words[j])){
                items.put(words[i], new Item(words[i],rooms.get(key)));
            }
        }
                
        i = i + 2;
        j = j + 2;
    }
    System.out.println("added: " + items.keySet());
}
    
private void roomToHash(String[] words){
    int i = 1;
    int j = 2;
    while (j < words.length){
        rooms.put(words[i], new Room(words[i], Boolean.parseBoolean(words[j])));
        i = i + 2;
        j = j + 2;
    }
    System.out.println("added: " + rooms.keySet());
} 
 

public HashMap<String, Room> getRooms()
{
    return new HashMap<>();
}

public HashMap<String, Item> getItems()
{
    return new HashMap<String, logic.elements.characters.Item>();
}




    
}
