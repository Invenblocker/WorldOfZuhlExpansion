package logic.elements.characters;

import acq.IItem;
import logic.elements.rooms.ItemRoom;

/**
 *
 * @author Invenblocker & JN97
 */
public class Item implements IItem
{
    private String name;
    private ItemRoom defaultRoom;
    
    /**
     * @author Invenblocker
     * 
     * Creates a new item with a name, but no default room.
     * 
     * @param name The name of the item.
     */
    public Item(String name)
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
    public Item(String name, ItemRoom defaultRoom)
    {
        this.name = name;
        this.defaultRoom = defaultRoom;
    }
    
    /**
     * @author JN97
     * returns item to the default room.
     */
    public void returnToDefaultRoom()
    {
        defaultRoom.setItem(this);
    }
    
    /**
     * @author JN97
     * 
     * A getter function for the name of the item.
     * 
     * @return The item's name.
     */
    @Override
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
    public ItemRoom getDefaultRoom()
    {
        return defaultRoom;
    }
    
    /**
     * @author JN97
     * @return The item's name,
     */
    @Override
    public String toString()
    {
        return this.getName(); 
    }
}
