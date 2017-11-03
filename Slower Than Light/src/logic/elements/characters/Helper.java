/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

import java.util.ArrayList;
import logic.elements.rooms.Room;

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
    
    
    public Helper(Room room, String name, double chanceOfDiscovery, double chanceOfDiscoveryGrowth)
    {
        super(room);
        this.name = name;
        this.chanceOfDiscovery = DEFAULT_CHANCE_OF_DISCOVERY = chanceOfDiscovery;
        CHANCE_OF_DISCOVERY_GROWTH = chanceOfDiscoveryGrowth;
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
    }
    
    
    private int search()
    {
        ArrayList<String> exits = getCurrentRoom().getCollectionOfExits();
        
        if(!getCurrentRoom().isControlRoom() && Math.random() < chanceOfDiscovery - CHANCE_OF_DISCOVERY_GROWTH)
        {
            
        }
        else
        {
            for(int i = exits.size() - 1; i >= 0; i--)
            {
                if(getCurrentRoom().getExit(exits.get(i)).isControlRoom())
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
    }
}
