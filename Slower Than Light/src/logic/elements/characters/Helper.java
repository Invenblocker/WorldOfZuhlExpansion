package logic.elements.characters;

import acq.IHelper;
import logic.SystemLog;
import java.util.ArrayList;
import java.util.List;
import logic.Game;
import logic.elements.rooms.Exit;
import logic.elements.rooms.Room;
import logic.elements.rooms.ItemRoom;

/**
 * A Helper who aids the player in the battle against the Saboteur.
 * 
 * @author Invenblocker & JN97
 */
public class Helper extends RoomHopper implements IHelper
{
    public final double DEFAULT_CHANCE_OF_DISCOVERY, CHANCE_OF_DISCOVERY_GROWTH;
    private double chanceOfDiscovery;
    private HelperTask task;
    private String name;
    private String foundItemString;
    private List<Room> returnRoute;
    private SystemLog ACTION_LOG, ERROR_LOG;
    
    /**
     * A constructor used to create a new Helper.
     * 
     * @author JN97
     * @param room The Room the Helper should be located in at the start of the
     *             game.
     * @param name The Helper's Name.
     * @param chanceOfDiscovery The Helper's default chance of finding a special
     *                          Item while seaching.
     * @param chanceOfDiscoveryGrowth  The amount by which the Helper's chance
     *                                 of finding an item increases every time
     *                                 it doesn't find one.
     */
    public Helper(Room room, String name, double chanceOfDiscovery, double chanceOfDiscoveryGrowth)
    {
        super(room);
        this.name = name;
        this.chanceOfDiscovery = DEFAULT_CHANCE_OF_DISCOVERY = chanceOfDiscovery;
        CHANCE_OF_DISCOVERY_GROWTH = chanceOfDiscoveryGrowth;
        task = HelperTask.RETURN_TO_DEFAULT;
        returnRoute = new ArrayList();
        foundItemString = "";
        ACTION_LOG = new SystemLog("Helper (" + this.name + ')', SystemLog.getActionLog());
        ERROR_LOG = new SystemLog("Helper (" + this.name + ')', SystemLog.getErrorLog());
    }
    
    /**
     * The Helper's AI for when it is searching or returning to the ControlRoom.
     *
     * @author JN97
     * @return The amount of time befoire the next time the Helper should act.
     *         Returns -1 if the Helper shouldn't act in the first place.
     */
    public int performAction()
    {
        if (task != null) switch(task)
        {
            case SEARCH:
                return(search());
            case RETURN_TO_DEFAULT:
                return(returnToDefault());
            case BODYGUARD:
                ERROR_LOG.writeToLog("Helper.performAction() was called while the Helper \"" + name + "\" is set to bodyguard.");
                return(-1);
            default:
                setTask(HelperTask.RETURN_TO_DEFAULT);
                ERROR_LOG.writeToLog("Helper.performAction() was called while the Helper \"" + name + "\" had a task that was not recognized by the method.",
                        "As a failsafe, The Helper \"" + name + "\" had its task set to RETURN_TO_DEFAULT.");
                return((int) Math.floor(Math.random() * 6) + 5);
        }
        else
        {
            setTask(HelperTask.RETURN_TO_DEFAULT);
            ERROR_LOG.writeToLog("Helper.performAction() was called while the Helper \"" + name + "\" did not have a defined task.",
                        "As a failsafe, The Helper \"" + name + "\" had its task set to RETURN_TO_DEFAULT.");
            return((int) Math.floor(Math.random() * 6) + 5);
        }
    }
    
    
    /**
     * The AI that makes the Helper search for a special item.
     * 
     * @author Invenblocker & JN97
     * @return The amount of time till the next time the Helper should act.
     */
    private int search()
    {
        List<Exit> exits = getCurrentRoom().getCollectionOfExits();
        
        if((getCurrentRoom() instanceof ItemRoom) && Math.random() < chanceOfDiscovery - CHANCE_OF_DISCOVERY_GROWTH && ((ItemRoom) getCurrentRoom()).getSpecialItem() == null)
        {
            ArrayList<Item> specialItems = new ArrayList();
            
            for(Item specialItem : Game.getInstance().getSpecialItems().values())
            {
                specialItems.add(specialItem);
            }
            
            Item specialItem = specialItems.get((int) Math.floor(Math.random() * specialItems.size()));
            
            ((ItemRoom) getCurrentRoom()).setSpecialItem(specialItem);
            
            foundItemString = "I found " + specialItem.getName() + " in the " + getCurrentRoom().getName();
            
            ACTION_LOG.writeToLog("The Helper \"" + name + "\" found a \"" + specialItem.getName() + "\" in \"the " + getCurrentRoom().getName() + "\".");
            
            chanceOfDiscovery = DEFAULT_CHANCE_OF_DISCOVERY;
            
            setTask(HelperTask.RETURN_TO_DEFAULT);
        }
        else
        {
            for(int i = exits.size() - 1; i >= 0; i--)
            {
                if(!(getCurrentRoom().getExit(exits.get(i)) instanceof ItemRoom) || !exits.get(i).isOperating())
                {
                    exits.remove(i);
                }
            }
            if(exits.size() != 0)
            {
                setRoom(getCurrentRoom().getExit(exits.get((int) Math.floor(exits.size() * Math.random()))));
            }
            else
            {
                exits = getCurrentRoom().getCollectionOfExits();
                for(int i = exits.size() - 1; i >= 0; i--)
                {
                    if(!exits.get(i).isOperating())
                    {
                        exits.remove(i);
                    }
                }
                if(exits.size() != 0)
                {
                    setRoom(getCurrentRoom().getExit(exits.get((int) Math.floor(exits.size() * Math.random()))));
                }
                else
                {
                    ERROR_LOG.writeToLog("The Helper \"" + name + "\" could not find a valid exit from its current room which is \"the " + getCurrentRoom().getName() + "\".");
                }
            }
        }
        
        return(5 + (int) Math.floor(6 * Math.random()));
    }
    
    /**
     * Makes the Helper return to the ControlRoom.
     * 
     * @author JN97 & Invenblocker
     * @return A number describing the amount of time before the Helper should act again. Returns -1 if the Helper is already in the ControlRoom.
     */
    private int returnToDefault()
    {
        if(getCurrentRoom().isControlRoom())
        {
            return(-1);
        }
        else
        {
            if(returnRoute.size() == 0 || getCurrentRoom().getExit(returnRoute.get(0)).isOperating())
            {
                returnRoute = findReturnRoute();
            }
            
            setRoom(returnRoute.get(0));
            returnRoute.remove(0);
            
            return(5 + (int) Math.floor(6 * Math.random()));
        }
    }
    
    /**
     * Finds the shortest route to the control room and returns the route as an ArrayList of Rooms.
     *
     * @author Invenblocker
     * @return An ArrayList of Room instances that dictate the Helper's shortest route to a ControlRoom.
     */
    private List<Room> findReturnRoute()
    {
        ArrayList<ArrayList<Room>> routes = new ArrayList();
        boolean foundControlRoom = false;
        int routeLength = 0;
        //Create a new route and et the current room as the only room in it.
        //This route will be used as a starting point for generating other routes.
        routes.add(new ArrayList());
        routes.get(0).add(getCurrentRoom());
        
        while(!foundControlRoom)  //Loop until a controlRoom has been found
        {
            if(routeLength > Game.getInstance().getRooms().values().size() || routes.size() == 0)  //If the route's length exceeds that of the amount of rooms in the game or the list of routes i empty, assume an error has occured.
            {
                ERROR_LOG.writeToLog("No valid route to a control room was found.",  //Write a message into the error log.
                        "As a failsafe, the Helper will move from its current room to its current room");
                
                //In order to avoid a nullpointer exception, the method will in this failsafe state simply return the current room.
                ArrayList<Room> route = new ArrayList();
                route.add(getCurrentRoom());
                return(route);
            }
            for(int a = routes.size() - 1; a >= 0; a--)  //Loop backwards through all routes plotted so far.
            {
                ArrayList<Room> neighbors = new ArrayList();  //Create an ArrayList to store valid neighbors.
                Room checkRoom = routes.get(a).get(routeLength);  //Grabs the current last room on the route.
                for(Exit exit : checkRoom.getCollectionOfExits())  //Loops through the checkRoom's exits.
                {
                    if(!exit.isOperating())  //Ends the iteration if the exit cannot be used.
                    {
                        continue;
                    }
                    
                    if(checkRoom.getExit(exit).isControlRoom())
                    {
                        //If the neighbor is a control room, add it to the route and tell the system a control room has been found.
                        foundControlRoom = true;
                        neighbors.add(checkRoom.getExit(exit));
                        continue;
                    }
                    
                    if(!routes.get(a).contains(checkRoom.getExit(exit)))
                    {
                        //If the room is not on the current route, remove it.
                        neighbors.add(checkRoom.getExit(exit));
                    }
                }
                
                switch(neighbors.size())
                {
                    case 0:  //If no valid neighbors have been found, remove the current route from the list.
                        routes.remove(a);
                        break;
                    case 1:  //If exactly one valid neighbor has been found, add it to the current room.
                        routes.get(a).add(neighbors.get(0));
                        break;
                    default:  //If more than one valid neighbor has been found, add one of them to the current route and create new routes for the others.
                        for(int b = 1; b < neighbors.size(); b++)
                        {
                            ArrayList routeCopy = new ArrayList();
                            for(int c = 0; c < routes.get(a).size(); c++)
                            {
                                routeCopy.add(routes.get(a).get(c));
                            }
                            routeCopy.add(neighbors.get(b));
                            routes.add(routeCopy);
                        }
                        routes.get(a).add(neighbors.get(0));
                        break;
                }
            }
            routeLength++;
        }
        
        for(int i = routes.size() - 1; i >= 0; i--)
        {
            if(!routes.get(i).get(routeLength).isControlRoom())
            {
                routes.remove(i);  //Remove all routes that do not lead to a control room.
            }
            else
            {
                routes.get(i).remove(0);  //Remove the current room from the routes.
            }
        }
        
        ArrayList<Room> route = routes.get((int) Math.floor(Math.random() * routes.size()));  //Pick a valid route at random.
        
        String returnRoute = "";
        
        for(int i = 0; i < route.size(); i++)  //Generate a String describing the return route.
        {
            returnRoute += "goto \"the " + route.get(i).getName() + '"';
            if(i < routes.size() - 1)
            {
                returnRoute += ", then ";
            }
        }
        
        ACTION_LOG.writeToLog("The Helper \"" + name + "\" plotted the following route to the controlRoom: First " + returnRoute + '.');  //Write the route into the action log.
        
        return(route);  //Return the route that was created.
    }
    
    /**
     * @author Invenblocker
     * @return The Helper's current chance of making a discovery
     */
    public double getChanceOfDiscovery()
    {
        return(chanceOfDiscovery);
    }
    
    /**
     * @author Invenblocker
     * @param value What the chance of discovery should be set to
     */
    public void setChanceOfDiscovery(double value)
    {
        chanceOfDiscovery = value;
    }
    
    /**
     * @author Invenblocker
     * @return The helper's current task
     */
    public HelperTask getHelperTask()
    {
        return(task);
    }
    
    /**
     * @author JN97
     * @return The toString value of the Helper's current task.
     */
    public String getHelperTaskString()
    {
        return getHelperTask().toString();
    }
    
    /**
     * Changes the Helper's Task.
     * @author JN97
     * @param task The task that the Helper should start working on.
     */
    public void setTask(HelperTask task)
    {
        this.task = task;
        returnRoute = new ArrayList();
        ACTION_LOG.writeToLog("The helper \"" + name + "\" had its task set to \"" + task.toString() + "\".");
    }
    
    /**
     * @author Invenblocker
     * @return The Helper's NAME
     */
    public String getName()
    {
        return(name);
    }
    
    /**
     * @author Invenblocker
     * @param remove Defines if the String should be cleared after returning.
     * @return A String a string describing the special Item the Helper has 
     *         found, and the room it was located in.
     */
    public String getFoundItemString(boolean remove)
    {
        if(remove)
        {
            String output = foundItemString;
            foundItemString = "";
            return(output);
        }
        else
        {
            return(foundItemString);
        }
    }
    
    /**
     * This version of the method will always clear the String.
     * 
     * @author Invenblocker
     * @return A String a string describing the special Item the Helper has 
     *         found, and the room it was located in.
     */
    public String getFoundItemString()
    {
        return(getFoundItemString(true));
    }
    
    /**
     * @author Invenblocker
     * @return The Helper's ACTION_LOG
     */
    @Override
    public SystemLog getActionLog()
    {
        return(ACTION_LOG);
    }
    
    /**
     * @author Invenblocker
     * @return The Helper's ERROR_LOG
     */
    @Override
    public SystemLog getErrorLog()
    {
        return(ERROR_LOG);
    }

    /**
     * @author Invenblocker
     * @return A String detailing what the Helper should say.
     */
    public String generateHelperMessage()
    {
        if(foundItemString != "")
        {
            return(getFoundItemString());
        }
        else
        {
            if(task == null)
            {
                ERROR_LOG.writeToLog("Task is null", "As a failsafe, task has been set to RETURN_TO_DEFAULT");
                setTask(HelperTask.RETURN_TO_DEFAULT);
                return("I... Uhm... what exactly am I doing?");
            }
            else
            {
                switch(task)
                {
                    case SEARCH:
                        return("Oh hi, I'm still searching for something, I'll meet you in the control room when I've found it.");
                    case BODYGUARD:
                        return("Don't worry, I'll keep you safe, just don't do anything rash.");
                    case RETURN_TO_DEFAULT:
                        if(getCurrentRoom().isControlRoom()) return("I'm on my way to the control room, see you there.");
                        else return("It's scary with that saboteur around. Can't say I like the situation, but I'll do anything I can to help. Do you need me to do anything?");
                    default:
                        ERROR_LOG.writeToLog("Task is not recognized.", "As a failsafe, task has been set to RETURN_TO_DEFAULT");
                        return("I... Uhm... what exactly am I doing?");
                }
            }
        }
    }
}
