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
public class Player extends RoomHopper
{
    private Item[] inventory;
    
    /**
     * @author JN97
     * 
     * Creates a player and spawns them in the specified room.
     * 
     * @param room The player's starting room.
     */
    Player(Room room)
    {
        super(room);
        
        inventory = new Item[2];
    }
    
    /**
     * @author JN97
     * 
     * Adds an item to the inventory. Returns true if the item was successfully
     * added, returns false if the inventory is full.
     * 
     * @param item The item to be added.
     * @return A boolean stating if the item was succesfully added.
     */
    public boolean addItem(Item item)
    {
        for(int i = 0; i < inventory.length; i++)
        {
            if(inventory[i] == null)
            {
                inventory[i] = item;
                
                return true;
            }          
        }
        return false;
    }
    
    /**
     * @author JN97
     * 
     * Removes an item from the player's inventory and returns a boolean stating
     * if the item was in the inventory in the first place.
     * 
     * @param item The item that should be removed.
     * @return A boolean that is true if the removed item was in the inventory.
     */
    public boolean removeItem(Item item)
    {
        for(int i = inventory.length - 1; i >= 0; i--)
        {
            if(inventory[i] != null && inventory[i].equals(item))
            {
                inventory[i] = null;
                return true;                
            }
        }
        return false;
    }
    
    /**
     * @author Invenblocker
     * 
     * Removes the item at the inventory's given index.
     * Returns false if the index is out of bounds or if the slot is already
     * empty.
     * 
     * @param index The index of the item to be removed.
     * @return A boolean stating if the item was successfully removed.
     */
    public boolean removeItem(int index)
    {
        if(index < 0 || index >= inventory.length) return(false);
        if(inventory[index] == null)
        {
            return(false);
        }
        else
        {
            inventory[index] = null;
            return(true);
        }
    }
    
    /**
     * @author Invenblocker
     * 
     * Returns a copy of the player's inventory.
     * 
     * @return The inventory as an array passed by value.
     */
    public Item[] getInventory()
    {
        Item[] copy = new Item[inventory.length];
        
        for(int i = 0; i < inventory.length; i++)
        {
            copy[i] = inventory[i];
        }
        
        return(copy);
    }
}
