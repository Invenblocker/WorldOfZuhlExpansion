/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

/**
 *
 * @author Erik
 */
public class Player extends RoomHopper
{
    private Item[] inventory;
    
    public boolean addItem(Item item)
    {
        for(Item slot : inventory)
        {
            if(slot == null)
            {
                slot = item;
                
                return true;
            }          
        }
        return false;
    }
    
    public boolean removeItem(Item item)
    {
        for(Item slot : inventory)
        {
            if(slot == item)
            {
                slot = null;
                return true;                
            }
        }
        return false;
    }
    
    public Item[] getInventory()
    {
        int itemCount = 0;
        
        for(Item slot : inventory)
        {
            if(slot != null) itemCount++;
        }
        
        if(itemCount == 0) return(new Item[0]);
        
        Item[] croppedInventory = new Item[itemCount];
        
        int currentSlot = 0;
        
        for(Item slot : inventory)
        {
            if(slot != null)
            {
                croppedInventory[currentSlot++] = slot;
            }
        }
        
        return(croppedInventory);
    }
}
