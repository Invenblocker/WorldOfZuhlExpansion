package logic.elements.rooms;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Room 
{
    private String name;
    private boolean controlRoom; 
    private boolean operating;
    private HashMap<String, Room> exits;
    
    /**
     * A constructor that creates a new room with no exits.
     * @param name The name of the room.
     */
    public Room(String name, boolean controlRoom) 
    {
        this.controlRoom = controlRoom;
        this.name = name;
        exits = new HashMap<String, Room>();    //Creates an empty HashMap
    }

    /**
     * Adds an exit to the room defining where the player can go from said room.
     * @param direction The second word of the go command required to use this exit.
     * @param neighbor The room that the exit added to the current leads to.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
   
    
    public void setOperating(boolean value)
    {
        this.operating = value;
    }
    
     public boolean isOperating()
    {
        return this.operating;
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

    /**
     * Returns the room accessible from the specified exit.
     * @param direction The second word of the go command required to use this exit.
     * @return The room tht is located in the direction of the specified exit.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
        
    }
    
    public boolean isControlRoom()
    {
        return this.controlRoom;
    }
}

