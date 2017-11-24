/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acq;

import java.util.HashMap;
import java.util.LinkedHashMap;
import logic.elements.characters.Item;
import logic.elements.rooms.Room;

/**
 *
 * @author mortenskovgaard
 */
public interface ILoader
{
    void loadGame(String gameName);

    void newGame(String gameName);

    HashMap<String, Room> getRooms();

    HashMap<String, Item> getItems();

    HashMap<String, Item> getSpecialItems();
    
    ITimeHolder getTimeHolder();
    
    HashMap<String, String> getRoomsInfo();
    
    HashMap<String, String> getItemsInfo();
    
    HashMap<String, String> getSpecialItemsInfo();
    
    String getPlayerInfo();
    
    String getSaboteurInfo();

    String getHelperInfo();

    String getTimeHolderInfo();

    LinkedHashMap<String, Integer> getHighscore();
    
    int getRoomsRepaired ();
}
