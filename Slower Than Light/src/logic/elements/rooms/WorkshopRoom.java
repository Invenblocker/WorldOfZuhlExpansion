package logic.elements.rooms;

import java.util.ArrayList;
import java.util.List;
import logic.elements.characters.Item;

/**
 *
 * @author Peter
 */
public class WorkshopRoom  extends Room{
    private List<Item> items = new ArrayList<Item>();
    
    
    public WorkshopRoom(String name){ //constructs workshoproom
        super(name); 
        
    }
    
    public void addItem(Item item){ //Adds/stores item in workshoproom
        items.add(item);          
    }
    
    public void removeItem(Item item){ //Removes/takes item from workshoproom
        items.remove(item);
    }
    
    public List<Item> getItems(){  //returns all items in the array/workshoproom
        return this.items;     
        
    }
    
    
}
