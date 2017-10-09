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
    private String description;
    private HashMap<String, Room> exits;
    
    /**
     * A constructor that creates a new room with no exits.
     * @param description The description of the room.
     */
    public Room(String description) 
    {
        this.description = description;
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

    /**
     * Returns the description of the room.
     * @return The room's description.
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Returns a String informing the player of the current room as well as all
     * possible exits from the room.
     * @return The current room as well as where the player can go from said room.
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }
    
    /**
     * Returns a String containing a list of exits.
     * @return A String with a list of possible exits from the current room.
     */
    private String getExitString()
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
}

