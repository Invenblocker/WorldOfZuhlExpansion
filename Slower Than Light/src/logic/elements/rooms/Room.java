   package logic.elements.rooms;

import acq.IRoom;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import logic.SystemLog;
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
         this.repairTools = new ArrayList<>();
         this.operating = true;
         exits = new HashMap<>();    //Creates an empty HashMap  key/value
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
    /**
     * 
     * @return the room's name as a string
     */
    @Override
    public String toString()
    {
        return this.name;
    }
    
    @Override
      public boolean isOperating(){   //checks boolean value for room
        
        return this.operating;       //returns setOperatinge - dameged room or not
    }
    
    
    public void setOperating(boolean value) {     //Sets value  damged or not.
        this.operating = value;
        
        }

    public boolean isControlRoom() {    //returns boolean value, is player in CR
        return this.controlRoom;
    }

     public ArrayList<Tool> getRepairTools() { 
        return this.repairTools; // returnes the repair tool for the room
    }
     
      public void addRepairTools(Tool tool) {
        this.repairTools.add(tool); // add's a repair tool to the room.
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
    public Room getExit(String direction) 
    {
      
        
        if(!exits.keySet().contains(direction)) // Checks if there issent a direction to the exit
        {
            SystemLog.getErrorLog().writeToLog("The room \"" + this.getName() + "\" does not have an exit in the direction \"" + direction + "\"."); // write's it to the errorlog
            return null;
        }
        else
        {
            if(exits.get(direction) != null) // checks the direction of the exit
            {
                if(exits.get(direction).getExitRoom1().name.equals(this.name)){ // finds out what rooms there is on both sides of the exit.
                    Room exit = exits.get(direction).getExitRoom2();
                    
                    return exit;
                }
                else{
                   return exits.get(direction).getExitRoom1(); // returns the exit in the specific direction
                }
            }
            else
            {
                
                SystemLog.getErrorLog().writeToLog("The exit stored by the room \"" + this.getName() + "\" stores an empty exit in the direction \"" + direction + "\"."); // write's it to the errorlog
                return null;
            }
        }
    }
    /**
     * Checks for exit from exits in the exit map. 
     * @param exit
     * @return's exits for eihter exitRoom 1 or exitRoom 2 or null if there 
     * isent any exit's
     */
    public Room getExit(Exit exit)
    {
        if(exits.values().contains(exit))
        {
            if(exit.getExitRoom1().equals(this))
                return(exit.getExitRoom2());
            else
                return(exit.getExitRoom1());
        }
        else
        {
            System.out.println(exit.toString() + " is not an exit from " + getName());
            return null;
        }
    }
    /**
     * Goes though the exit map. and compares the exit to first exitRoom 2 and 
     * the exitRoom 1. 
     * @param room
     * @return the exit for the room
     */
    public Exit getExit(Room room)
    {
        for(Exit exit : exits.values()){
            System.out.println("Compares: " + exit.getExitRoom2() + " and " + room + " || " + exit.getExitRoom1() + " and " + this);
            if(exit.getExitRoom2().equals(room) || exit.getExitRoom1().equals(room)){
                
                return(exit);
            }
            
                
        }
        return null;
    }
    
   /**
     * Adds an exit to the room defining where the player can go from said room.
     * @param name The second word of the go command required to use this exit.
     * @param neighbor The room that the exit added to the current leads to.
     */
    public void setExit(String direction, Exit neighbor) 
    {
        exits.put(direction, neighbor);    
    }
}