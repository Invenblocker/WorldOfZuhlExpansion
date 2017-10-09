/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

import logic.elements.rooms.*;

/**
 *
 * @author barth_000
 */
public class RoomHopper
{
    private Room currentRoom;
    
    RoomHopper(Room room)
    {
        currentRoom = room;
    }
    
    public Room setRoom(Room newRoom)
    {
        Room oldRoom = currentRoom;
        currentRoom = newRoom;
        return(oldRoom);
    }
    
    public Room getCurrentRoom()
    {
        return(currentRoom);
    }
}
