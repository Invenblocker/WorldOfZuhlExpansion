   package logic.elements.rooms;

import ACQ.IRoom;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import logic.SystemLog;
import logic.elements.characters.Item;
import logic.elements.characters.Tool;


public class Room implements IRoom 
{
    private String name;
    private boolean operating;         //is room damaged
    private boolean controlRoom;       //check if player is in CR
    private ArrayList<Tool> repairTools;
    private HashMap<String, Exit> exits;
    
    /**
     * A constructor that creates a new room with no exits.
     * @param name The name of the room.
     */
    
    public Room(String name){
         this.controlRoom = false;     //constructs value for controlroom 
         this.name = name;
         this.repairTools = new ArrayList<Tool>();
         this.operating = true;
         exits = new HashMap<String, Exit>();    //Creates an empty HashMap  key/value
    }
    
    public Room(String name, boolean controlRoom) //constructor for  boolean CR
    {
        this(name);
        this.controlRoom = controlRoom;
    }

    

     /**
     * Returns the name of the room.
     * @return The room's name.
     */
    @Override
    public String getName()
    {
        return this.name;
    }
    
    @Override
      public boolean isOperating(){   //checks boolean value for room
        
        return this.operating;       //returns setOperatinge - dameged room or not
    }
    
    
    @Override
    public void setOperating(boolean value) {     //Sets value  damged or not.
        this.operating = value;
        
        }

    @Override
    public boolean isControlRoom() {    //returns boolean value, is player in CR
        return this.controlRoom;
    }

    @Override
     public ArrayList<Tool> getRepairTools() {
        return this.repairTools;
    }
     
    @Override
      public void addRepairTools(Tool tool) {
        this.repairTools.add(tool);
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

     
    @Override
    public ArrayList<Exit> getCollectionOfExits(){
        ArrayList<Exit> list = new ArrayList<Exit>();   // creates an arraylist
          
        Set<String> keys = exits.keySet();              // creates a set of keys from the hashmap exits.
        for(String exit : keys)                         // loops through the keys (from the set of keys)
            list.add(exits.get(exit));                  // adds the exit to the arraylist via. exit(value)
        
        return list;                                    // returns the list.
    }
    /**
     * Returns the room accessible from the specified exit.
     * @param direction The second word of the go command required to use this exit.
     * @return The room tht is located in the direction of the specified exit.
     */
    @Override
    public Room getExit(String direction) 
    {
        if(exits.keySet().contains(direction))
        {
            SystemLog.getErrorLog().writeToLog("The room \"" + this.getName() + "\" does not have an exit in the direction \"" + direction + "\".");
            return null;
        }
        else
        {
            if(exits.get(direction) != null)
            {
                if(exits.get(direction).getExitRoom1().name.equals(this.name))
                    return exits.get(direction).getExitRoom2();
                else
                   return exits.get(direction).getExitRoom1();
            }
            else
            {
                SystemLog.getErrorLog().writeToLog("The exit stored by the room \"" + this.getName() + "\" stores an empty exit in the direction \"" + direction + "\".");
                return null;
            }
        }
    }
    
    @Override
    public Room getExit(Exit direction)
    {
        if(exits.values().contains(direction))
        {
            if(direction.getExitRoom1().equals(this))
                return(direction.getExitRoom2());
            else
                return(direction.getExitRoom1());
        }
        else
        {
            System.out.println(direction.toString() + " is not an exit from " + getName());
            return null;
        }
    }
    
    @Override
    public Exit getExit(Room room)
    {
        for(Exit exit : exits.values())
            if(getExit(exit).equals(room))
                return(exit);
        
        return null;
    }
    
   /**
     * Adds an exit to the room defining where the player can go from said room.
     * @param name The second word of the go command required to use this exit.
     * @param neighbor The room that the exit added to the current leads to.
     */
    @Override
    public void setExit(String direction, Exit neighbor) 
    {
        exits.put(direction, neighbor);    
    }
}