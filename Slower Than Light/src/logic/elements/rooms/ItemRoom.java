/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.rooms;

/**
 *
 * @author Erik
 */
public class ItemRoom extends Room{
    private Item item;
    private Item specialItem;
    private String repairItem;

    public ItemRoom(String name, String repairItem) {
        super(name, false);
    }
    
    public void setItem(Item item) 
    {
        this.item = item;
    }
     public void setSpecialItem(Item specialItem) 
    {
        this.specialItem = specialItem;
    }
     
     public void takeItem(Item item){
         this.item = null;
     }
     
     public void takeSpecialItem(Item item){
         this.specialItem = null;
     }
     
     public Item getItem(){
         return this.item;
     }
     
      public Item getSpecialItem(){
         return this.SpecialItem;
     }
     
      public Item getRepairItem(){
          return this.repairItem;
      }
}
