/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acq;

import logic.elements.rooms.Room;

/**
 *
 * @author mortenskovgaard
 */
public interface IRoomHopper 
{

    /**
     * @author Invenblocker
     *
     * Checks which room the character is currently in.
     *
     * @return The room in which the character is currently located.
     */
    Room getCurrentRoom();
    
}
