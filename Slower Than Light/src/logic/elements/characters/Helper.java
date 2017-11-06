/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

import java.util.ArrayList;
import logic.Game;
import logic.elements.rooms.Room;
import logic.elements.rooms.ItemRoom;

/**
 *
 * @author barth_000
 */
public class Helper extends RoomHopper
{
    private final double DEFAULT_CHANCE_OF_DISCOVERY, CHANCE_OF_DISCOVERY_GROWTH;
    private HelperTask task;
    private String name;
    private double chanceOfDiscovery;
    private Room foundItemRoom;
    private Item foundSpecialItem;
    private ArrayList<Room> returnRoute;
    
    
    public Helper(Room room, String name, double chanceOfDiscovery, double chanceOfDiscoveryGrowth)
    {
        super(room);
        this.name = name;
        this.chanceOfDiscovery = DEFAULT_CHANCE_OF_DISCOVERY = chanceOfDiscovery;
        CHANCE_OF_DISCOVERY_GROWTH = chanceOfDiscoveryGrowth;
        task = HelperTask.RETURN_TO_DEFAULT;
        returnRoute = new ArrayList();
        foundItemRoom = null;
        foundSpecialItem = null;
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
                System.out.println("performAction() should not be called while the helper is set to bodyguard.");
                return(-1);
            default:
                System.out.println("No task has been defined for the helper.");
                return(-1);
        }
        else
        {
            System.out.println("The task is null???");
            return(-1);
        }
    }
    
    
    private int search()
    {
        ArrayList<String> exits = getCurrentRoom().getCollectionOfExits();
        
        if((getCurrentRoom() instanceof ItemRoom) && Math.random() < chanceOfDiscovery - CHANCE_OF_DISCOVERY_GROWTH)
        {
            foundItemRoom = getCurrentRoom();
            
            ArrayList<Item> specialItems = new ArrayList();
            
            for(Item specialItem : Game.getInstance().getSpecialItems().values())
            {
                specialItems.add(specialItem);
            }
            
            foundSpecialItem = specialItems.get((int) Math.floor(Math.random() * specialItems.size()));
            
            chanceOfDiscovery = DEFAULT_CHANCE_OF_DISCOVERY;
            
            task = HelperTask.RETURN_TO_DEFAULT;
        }
        else
        {
            for(int i = exits.size() - 1; i >= 0; i--)
            {
                if(!(getCurrentRoom().getExit(exits.get(i)) instanceof ItemRoom))
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
                System.out.println("No valid exits found");
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
            if(returnRoute.size() == 0)
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
                for(String exit : checkRoom.getCollectionOfExits())
                {
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
        
        return(routes.get((int) Math.floor(Math.random() * routes.size())));
    }
    
    public HelperTask getHelperTask()
    {
        return(task);
    }
    
    public void setTask(HelperTask task)
    {
        this.task = task;
    }
}
