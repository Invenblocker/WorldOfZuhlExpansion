/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acq;

import java.util.LinkedHashMap;
import logic.elements.rooms.Room;

/**
 *
 * @author mortenskovgaard
 */
public interface IGameInfo {

    Room[] getDestroyedRooms();

    LinkedHashMap<String, Integer> getHighScoreMap();

    boolean isGameFinished();

    LinkedHashMap<String, Integer> saveHighScore(String playerName);
    
    int getScore();
    
}
