/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acq;

import java.util.ArrayList;
import logic.elements.characters.Tool;
import logic.elements.rooms.Exit;
import logic.elements.rooms.Room;

/**
 *
 * @author Johnn
 */
public interface IRoom {

    void addRepairTools(Tool tool);

    ArrayList<Exit> getCollectionOfExits();

    /**
     * Returns the room accessible from the specified exit.
     * @param direction The second word of the go command required to use this exit.
     * @return The room tht is located in the direction of the specified exit.
     */
    Room getExit(String direction);

    Room getExit(Exit direction);

    Exit getExit(Room room);

    /**
     * Returns the name of the room.
     * @return The room's name.
     */
    String getName();

    ArrayList<Tool> getRepairTools();

    boolean isControlRoom();

    boolean isOperating();

    /**
     * Adds an exit to the room defining where the player can go from said room.
     * @param name The second word of the go command required to use this exit.
     * @param neighbor The room that the exit added to the current leads to.
     */
    void setExit(String direction, Exit neighbor);

    void setOperating(boolean value);
    
}
