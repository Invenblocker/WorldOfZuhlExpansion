/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acq;

import java.util.List;
import java.util.Map;
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

    void saveGameFromObjects(Map<String, Room> rooms, Map<String, Item> items, Player player, Saboteur saboteur, Helper helper, int roomsRepaired, TimeHolder time, String saveName);
    
    void saveGame (String roomsInfo, String itemsInfo, String specialItemsInfo,
                    List<String> exitInfo, String playerInfo,String saboteurInfo,
                    String helperInfo, String timeHolderInfo, int roomsRepaired);
    
    void writeHighScore(Map<String, Integer> highScore, String highscoreName);
    
}
