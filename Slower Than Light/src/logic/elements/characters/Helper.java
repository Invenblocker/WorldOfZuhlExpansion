/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

import database.SystemLog;
import java.util.ArrayList;
import logic.Game;
import logic.elements.rooms.Exit;
import logic.elements.rooms.Room;
import logic.elements.rooms.ItemRoom;

/**
 *
 * @author barth_000
 */
public class Helper extends RoomHopper
{
    public final double DEFAULT_CHANCE_OF_DISCOVERY, CHANCE_OF_DISCOVERY_GROWTH;
    private HelperTask task;
    private String name;
    private double chanceOfDiscovery;
    private String foundItemString;
    private ArrayList<Room> returnRoute;
    
    
    public Helper(Room room, String name, double chanceOfDiscovery, double chanceOfDiscoveryGrowth)
    {
        super(room);
        this.name = name;
        this.chanceOfDiscovery = DEFAULT_CHANCE_OF_DISCOVERY = chanceOfDiscovery;
        CHANCE_OF_DISCOVERY_GROWTH = chanceOfDiscoveryGrowth;
        task = HelperTask.RETURN_TO_DEFAULT;
        returnRoute = new ArrayList();
        foundItemString = "";
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
                SystemLog.getErrorLog().writeToLog("Helper.performAction() was called while the Helper \"" + name + "\" is set to bodyguard.");
                return(-1);
            default:
                setTask(HelperTask.RETURN_TO_DEFAULT);
                SystemLog.getErrorLog().writeToLog("Helper.performAction() was called while the Helper \"" + name + "\" had a task that was not recognized by the method.",
                        "As a failsafe, The Helper \"" + name + "\" had its task set to RETURN_TO_DEFAULT.");
                return((int) Math.floor(Math.random() * 6) + 5);
        }
        else
        {
            setTask(HelperTask.RETURN_TO_DEFAULT);
            SystemLog.getErrorLog().writeToLog("Helper.performAction() was called while the Helper \"" + name + "\" did not have a defined task.",
                        "As a failsafe, The Helper \"" + name + "\" had its task set to RETURN_TO_DEFAULT.");
            return((int) Math.floor(Math.random() * 6) + 5);
        }
    }
    
    
    private int search()
    {
        ArrayList<Exit> exits = getCurrentRoom().getCollectionOfExits();
        
        if((getCurrentRoom() instanceof ItemRoom) && Math.random() < chanceOfDiscovery - CHANCE_OF_DISCOVERY_GROWTH && ((ItemRoom) getCurrentRoom()).getSpecialItem().equals(null))
        {
            ArrayList<Item> specialItems = new ArrayList();
            
            for(Item specialItem : Game.getInstance().getSpecialItems().values())
            {
                specialItems.add(specialItem);
            }
            
            Item specialItem = specialItems.get((int) Math.floor(Math.random() * specialItems.size()));
            
            ((ItemRoom) getCurrentRoom()).setSpecialItem(specialItem);
            
            foundItemString = "I found " + specialItem.getName() + " in the " + getCurrentRoom().getName();
            
            SystemLog.getActionLog().writeToLog("The Helper \"" + name + "\" found a \"" + specialItem.getName() + "\" in \"the " + getCurrentRoom().getName() + "\".");
            
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
                    SystemLog.getErrorLog().writeToLog("The Helper \"" + name + "\" could not find a valid exit from its current room which is \"the " + getCurrentRoom().getName() + "\".");
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
    
    ArrayList<Room> findReturnRoute()
    {
        ArrayList<ArrayList<Room>> routes = new ArrayList();
        boolean foundControlRoom = false;
        int routeLength = 0;
        routes.add(new ArrayList());
        routes.get(0).add(getCurrentRoom());
        
        while(!foundControlRoom)
        {
            for(int a = routes.size() - 1; a >= 0; a--)
            {
                ArrayList<Room> exits = new ArrayList();
                Room checkRoom = routes.get(a).get(routeLength);
                for(Exit exit : checkRoom.getCollectionOfExits())
                {
                    if(!exit.isOperating())
                    {
                        break;
                    }
                    
                    if(checkRoom.getExit(exit).isControlRoom())
                    {
                        foundControlRoom = true;
                    }
                    
                    if(!routes.get(a).contains(checkRoom.getExit(exit)))
                    {
                        exits.add(checkRoom.getExit(exit));
                    }
                }
                
                switch(exits.size())
                {
                    case 0:
                        routes.remove(a);
                        break;
                    case 1:
                        routes.get(a).add(exits.get(0));
                        break;
                    default:
                        for(int b = 1; b < exits.size(); b++)
                        {
                            ArrayList routeCopy = new ArrayList();
                            for(int c = 0; c < routes.get(a).size(); c++)
                            {
                                routeCopy.add(routes.get(a).get(c));
                            }
                            routeCopy.add(exits.get(b));
                            routes.add(routeCopy);
                        }
                        routes.get(a).add(exits.get(0));
                        break;
                }
            }
            routeLength++;
        }
        
        for(int i = routes.size() - 1; i >= 0; i--)
        {
            if(!routes.get(i).get(routeLength).isControlRoom())
            {
                routes.remove(i);
            }
            else
            {
                routes.get(i).remove(0);
            }
        }
        
        ArrayList<Room> route = routes.get((int) Math.floor(Math.random() * routes.size()));
        
        String returnRoute = "";
        
        for(int i = 0; i < route.size(); i++)
        {
            returnRoute += "goto \"the " + route.get(i).getName() + '"';
            if(i < routes.size() - 1)
            {
                returnRoute += ", then ";
            }
        }
        
        SystemLog.getActionLog().writeToLog("The Helper \"" + name + "\" plotted the following route to the controlRoom: First " + returnRoute + '.');
        
        return(route);
    }
    
    public HelperTask getHelperTask()
    {
        return(task);
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
        SystemLog.getActionLog().writeToLog("The helper \"" + name + "\" had its task set to \"" + task.toString() + "\".");
    }
    
    public Room SetRoom(Room newRoom)
    {
        Room oldRoom = super.setRoom(newRoom);
        SystemLog.getActionLog().writeToLog("The helper \"" + name + "\" moved from \"the " + oldRoom.getName() + "\" to \"the " + newRoom.getName() + "\".");
        return(oldRoom);
    }
}
