/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acq;

import logic.elements.characters.Item;

/**
 *
 * @author mortenskovgaard
 */
public interface IPlayer extends IRoomHopper
{
    /**
     * @author Invenblocker
     *
     * Returns a copy of the player's inventory.
     *
     * @return The inventory as an array passed by value.
     */
    Item[] getInventory();

}
    

