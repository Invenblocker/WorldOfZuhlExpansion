/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACQ;

import logic.elements.rooms.ItemRoom;

/**
 *
 * @author mortenskovgaard
 */
public interface IItem {

    /**
     * @author JN97
     *
     * A getter function for the default room of the item.
     *
     * @return The item's default room.
     */
    ItemRoom getDefaultRoom();

    /**
     * @author JN97
     *
     * A getter function for the name of the item.
     *
     * @return The item's name.
     */
    String getName();

    void returnToDefaultRoom();
    
}
