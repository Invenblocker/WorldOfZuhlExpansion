/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.rooms;

/**
 *
 * @author Loc
 */
public class Exit {
    
    private Room exitRoom1;
    private Room exitRoom2;
    private boolean operating;
    
  Exit(Room exitRoom1, Room exitRoom2)
  {
      this.exitRoom1 = exitRoom1;       //first exit that a room can have
      this.exitRoom2 = exitRoom2;       //second exit that a room can have
      this.operating = true;
      
  }
  
  public boolean isOperating()          //Checks if door is damaged
  {
      return operating;
  }
  
  public void setOpreating(boolean value)  //Sets whether door is functional or not
  {
      this.operating = value;
      
  }
  
}


