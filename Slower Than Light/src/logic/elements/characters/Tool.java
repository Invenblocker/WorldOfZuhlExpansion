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
    Tool(String name)
    {
        super(name);
    }
    
    Tool(String name, Room defaultRoom)
    {
        super(name, defaultRoom);
    }
    
    public String getType()
    {
        return("tool");
    }
}
