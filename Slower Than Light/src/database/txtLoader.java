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
            roomToHashMap(words);
        }
        else if(words[0].equals("Item:")){
            itemToHashMap(words);
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
    
  }
  
private void itemToHashMap(String[] words){
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


//Method inserts room to our hasmap rooms
//Private as the method is only used and accessed in the txtLoader class
private void roomToHashMap(String[] words) {
        
     int i = 1;                 //index for room in our txt file            
     int j = 2;                 //index for boolean in our txt file
     
     
        //As long as j is less than our array words[] insert room to our
        //Array rooms[] 
        //rooms has the parameteres (String, room), but room has parameters
        //(String boolean)  explains parameteres inside while loop.
    while (j < words.length) {          //As long j is less than array lenght put room
        rooms.put(words[i], new Room(words[i], Boolean.parseBoolean(words[j]) ));   //read as string, force to bool
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
