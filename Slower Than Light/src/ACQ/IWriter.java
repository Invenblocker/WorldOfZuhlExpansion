/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACQ;

import java.awt.Point;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mortenskovgaard
 */
public interface IWriter
{
    void saveGame (String roomsInfo, String itemsInfo, String specialItemsInfo,
                    List<String> exitInfo, String playerInfo,String saboteurInfo,
                    String helperInfo, String timeHolderInfo, int roomsRepaired, Map<String, Point> roomPosition);
    
    void writeHighScore(Map<String, Integer> highScore, String highscoreName);
    
}
