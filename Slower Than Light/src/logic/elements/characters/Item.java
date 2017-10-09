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
    
    Item(String name, Room defaultRoom)
    {
        this.name = name;
        this.defaultRoom = defaultRoom;
    }
    
    public String getName()
    {
        return(name);
    }
    
    public Room getDefaultRoom()
    {
        return defaultRoom;
    }
    
    public String getType()
    {
        return("item");
    }
}
