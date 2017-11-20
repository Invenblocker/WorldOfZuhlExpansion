/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACQ;

import acq.IRoom;
import acq.IItem;
/**
 *
 * @author mortenskovgaard
 */
public interface IPlayerInfo
{
    
    IRoom currentRoom();
    
    int inventorySize();
    
    IItem itemOne();
    
    IItem itemtwo();
    
}
