/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

import acq.IHelper;
import logic.SystemLog;
import java.util.ArrayList;
import logic.Game;
import logic.elements.rooms.Exit;
import logic.elements.rooms.Room;
import logic.elements.rooms.ItemRoom;

/**
 *
 * @author barth_000
 */
public class Helper extends RoomHopper implements IHelper
{
    public final double DEFAULT_CHANCE_OF_DISCOVERY, CHANCE_OF_DISCOVERY_GROWTH;
    private HelperTask task;
    private String name;
    private double chanceOfDiscovery;
    private String foundItemString;
    private ArrayList<Room> returnRoute;
    private SystemLog ACTION_LOG, ERROR_LOG;
    
    
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
    
    
    private int search()
    {
        ArrayList<Exit> exits = getCurrentRoom().getCollectionOfExits();
        
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
	 *
	 * @author Invenblocker
	 *
	 * Finds the shortest route to the control room and returns the route as an ArrayList of Rooms.
	 *
	 * @return An ArrayList of Room instances that dictate the Helper's shortest route to a ControlRoom.
	 */
	private ArrayList<Room> findReturnRoute()
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
            if(routeLength > Game.getInstance().getRooms().values().size())  //If the route's length exceeds that of the amount of rooms in the game, assume an error has occured.
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
    
    public HelperTask getHelperTask()
    {
        return(task);
    }
    
    @Override
    public String getHelperTaskString()
            {
                return getHelperTask().toString();
            }
    
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
    
    public String getFoundItemString()
    {
        return(getFoundItemString(true));
    }
    
    public void setTask(HelperTask task)
    {
        this.task = task;
        ACTION_LOG.writeToLog("The helper \"" + name + "\" had its task set to \"" + task.toString() + "\".");
    }
    
    public SystemLog getActionLog()
    {
        return(ACTION_LOG);
    }
    
    public SystemLog getErrorLog()
    {
        return(ERROR_LOG);
    }
    
    public String getName()
    {
        return(name);
    }
    
    public double getChanceOfDiscovery()
    {
        return(chanceOfDiscovery);
    }
    
    public void setChanceOfDiscovery(double value)
    {
        chanceOfDiscovery = value;
    }

    public String generateHelperMessage()
    {
        if(foundItemString != "")
        {
            String message = foundItemString;
            foundItemString = "";
            return(message);
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
                switch(HelperTask)
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
