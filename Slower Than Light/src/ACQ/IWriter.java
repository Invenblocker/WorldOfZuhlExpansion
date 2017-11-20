/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acq;

import java.util.HashMap;
import java.util.LinkedHashMap;
import logic.elements.characters.Helper;
import logic.elements.characters.Item;
import logic.elements.characters.Player;
import logic.elements.characters.Saboteur;
import logic.elements.rooms.Room;
import logic.processors.TimeHolder;

/**
 *
 * @author mortenskovgaard
 */
public interface IWriter {

    void saveGame(HashMap<String, Room> rooms, HashMap<String, Item> items, Player player, Saboteur saboteur, Helper helper, int roomsRepaired, TimeHolder time, String saveName);

    void writeHighScore(LinkedHashMap<String, Integer> highScore, String highscoreName);
    
}