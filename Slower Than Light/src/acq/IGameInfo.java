/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acq;

import java.util.Map;

/**
 *
 * @author mortenskovgaard
 */
public interface IGameInfo {

    IRoom[] getDestroyedRooms();

    Map<String, Integer> getHighScoreMap();

    boolean isGameFinished();

    Map<String, Integer> saveHighScore(String playerName);
    
    int getScore();
    
}
