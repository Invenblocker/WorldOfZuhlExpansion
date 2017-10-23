/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

import logic.elements.rooms.*;

/**
 *
 * @author Invenblocker & JN97
 */
public class Item
{
    private String name;
    private Room defaultRoom;
    
    /**
     * @author Invenblocker
     * 
     * Creates a new item with a name, but no default room.
     * 
     * @param name The name of the item.
     */
    Item(String name)
    {
        this.name = name;
        this.defaultRoom = null;
    }
    
    /**
     * @author Invenblocker
     * 
     * Creates a new item with a name and a default room.
     * 
     * @param name The name of the item.
     * @param defaultRoom The default room for the item.
     */
    Item(String name, Room defaultRoom)
    {
        this.name = name;
        this.defaultRoom = defaultRoom;
    }
    
    /**
     * @author JN97
     * 
     * A getter function for the name of the item.
     * 
     * @return The item's name.
     */
    public String getName()
    {
        return(name);
    }
    
    /**
     * @author JN97
     * 
     * A getter function for the default room of the item.
     *
     * @return The item's default room.
     */
    public Room getDefaultRoom()
    {
        return defaultRoom;
    }
}
