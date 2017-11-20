/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acq;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import logic.elements.characters.Item;
import logic.elements.characters.Player;
import logic.elements.rooms.Room;

/**
 *
 * @author mortenskovgaard
 */
public interface ILoader {

    LinkedHashMap<String, Integer> getHighscore();

    HashMap<String, Item> getItems();

    Player getPlayer();

    HashMap<String, Room> getRooms();

    HashMap<String, Item> getSpecialItems();

    void loadGame(String gameName);

    /**
     * Takes the name of a txt file containing rooms and their exits, and items and their room.
     * Then puts the rooms into the rooms HashMap with their exits, and puts the items into the items HashMap, with their respective rooms.
     * @param gameName
     * @throws FileNotFoundException
     */
    void newGame(String gameName);
    
}
