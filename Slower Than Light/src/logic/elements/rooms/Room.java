package logic.elements.rooms;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import logic.elements.characters.Tool;


/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Room 
{
    private String name;
    private HashMap<String, Room> exits;
    private boolean controlRoom;       //check if player is in CR
    private boolean operating;          //is room damaged
    private Tool repairTool;
    
    /**
     * A constructor that creates a new room with no exits.
     * @param name The name of the room.
     */
    public Room(String name, boolean controlRoom) //constructor for  boolean CR
    {
        this.controlRoom = controlRoom;     //constructs value for controlroom 
        this.name = name;
        exits = new HashMap<String, Room>();    //Creates an empty HashMap  key/value
    }

    /**
     * Adds an exit to the room defining where the player can go from said room.
     * @param name The second word of the go command required to use this exit.
     * @param neighbor The room that the exit added to the current leads to.
     */
    public void setExit(String direction, Room neighbor) 
    {
        
        exits.put(direction, neighbor);
    }

    
    public void setOperating(boolean value) {     //Sets value  damged or not.
        this.operating = value;
        
        }
    
    public boolean isOperating(){   //checks boolean value for room
        
        return this.operating;       //returns setOperatinge - dameged room or not
    }
    
    
    /**
     * Returns the name of the room.
     * @return The room's name.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Returns a String informing the player of the current room as well as all
     * possible exits from the room.
     * @return The current room as well as where the player can go from said room.
     */

    
    /**
     * Returns a String containing a list of exits.
     * @return A String with a list of possible exits from the current room.
     */
    private String getExits()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    
    public ArrayList<String> getCollectionOfExits(){
        ArrayList<String> list = new ArrayList<String>();  // creates an arraylist
          
        Set<String> keys = exits.keySet();                 // creates a set of keys from the hashmap exits.
        for(String exit : keys) {                          // loops through the keys (from the set of keys)
            list.add(exit);                                // adds the exit to the arraylist
        }
        
        
        return list;                                       // returns the list.
    }

    /**
     * Returns the room accessible from the specified exit.
     * @param direction The second word of the go command required to use this exit.
     * @return The room tht is located in the direction of the specified exit.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    public boolean isControlRoom() {    //returns boolean value, is player in CR
        return this.controlRoom;
    }

    public Tool getRepairTool() {
        return repairTool;
    }
    
    
    
}

