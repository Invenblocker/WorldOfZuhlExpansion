package logic.elements.rooms;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import logic.elements.characters.Item;
import logic.elements.rooms.Room;
/**
 *
 * @author Erik
 */
public class ItemRoom extends Room{ //Itemroom inherits funnctionality from room
    private Item item;              //Items from class player.
    private Item specialItem;   
    private String repairItem;

    
    /*
    Constructor for Itemroom, which extends/inherits functionality from room.
    */
    public ItemRoom(String name, String repairItem) {
      super(name, false);//extends room, uses name, and false for cr, as itemroom can't be controlroom
    }
    
    
     //functionality
    public void setItem(Item item)                //Sets item in room class
    {
        this.item = item;          
    }
     public void setSpecialItem(Item specialItem) //Sets specialItem in room class
    {
        this.specialItem = specialItem;
    }
     
     public void takeItem(Item item){             //removes item from room when taken by player
         this.item = null;
     }
     
     public void takeSpecialItem(Item item){     //removes item from room when taken by player
         this.specialItem = null;
     }
     
     public Item getItem(){                     //returns item from datatype Item
         return this.item;
     }
     
      public Item getSpecialItem(){             //returns item from datatype Item
         return this.specialItem;
     }
     
      public String getRepairItem(){            //returns item from datatype String
          return this.repairItem;
      }
}
