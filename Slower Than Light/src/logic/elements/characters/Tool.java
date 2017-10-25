/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

import logic.elements.rooms.*;

/**
 *
 * @author barth_000
 */
public class Tool extends Item
{
    /**
     * @author Invenblocker
     * 
     * A constructor that creates a tool with a name, but no default room.
     * 
     * @param name The name of the item.
     */
    public Tool(String name)
    {
        super(name);
    }
    
    /**
     * @author JN97
     * 
     * A constructor that creates a tool with a name and a default room.
     * 
     * @param name The name of the tool.
     * @param defaultRoom The default room of the tool.
     */
    public Tool(String name, Room defaultRoom)
    {
        super(name, defaultRoom);
    }
}
