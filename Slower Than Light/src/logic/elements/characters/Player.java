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
public class Player {
    private Item[] inventory;
    
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
