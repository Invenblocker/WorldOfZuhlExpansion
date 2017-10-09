/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.rooms;

import java.util.ArrayList;
import logic.elements.characters.Item;

/**
 *
 * @author Peter
 */
public class WorkshopRoom  extends ItemRoom{
    private ArrayList<Item> items = new ArrayList<Item>();
    
    
    public WorkshopRoom(String name, String repairItem){ //constructs workshoproom
        super(name, repairItem); 
        
    }
    
    public void addItem(Item item){ //Adds/stores item in workshoproom
        items.add(item);          
    }
    
    public void removeItem(Item item){ //Removes/takes item from workshoproom
        items.remove(item);
    }
    
    public ArrayList<Item> getItems(){  //returns all items in the array/workshoproom
        return this.items;     
        
    }
    
    
}
