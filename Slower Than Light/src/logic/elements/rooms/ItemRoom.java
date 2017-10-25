package logic.elements.rooms;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import logic.elements.characters.Item;
import logic.elements.characters.Tool;
import logic.elements.rooms.Room;
/**
 *
 * @author Erik
 */
public class ItemRoom  extends Room{  //Itemroom inherits funnctionality from room
    private Item item;   //Item from subclass player.
    private Item specialItem;
   

    
    
    //Constructor for Itemroom, which extends/inherits functionality from room.
    public ItemRoom(String name, Tool repairTool) {
        super(name, false); //extends room, uses name, and false for cr, as itemroom can't be controlroom
        
        
    }
    
    //functionality
    
    public void setItem(Item item) {      //Sets item in room class
        this.item = item; 
    }
    
    public void setSpecialItem(Item specialItem) {  //Sets special item in room class
        this.specialItem = specialItem;
    }
    
    public void takeItem(Item item) {  //removes item when user takes it from the room
        this.item = null;
    }
            
    public void takeSpecialItem(Item item) {  //removes Specialitem when user takes it from the room
        this.specialItem = null;
    
}

    public Item getItem() {                 //returns item from datatype Item
        return item;
    }
 
    
    public Item getSpecialItem() {   //returns specialitem from datatype Item
        return specialItem;
    }
    

    
}