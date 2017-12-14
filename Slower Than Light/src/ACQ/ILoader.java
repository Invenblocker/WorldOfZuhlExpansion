package acq;

import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author mortenskovgaard
 */
public interface ILoader
{
    void loadGame(String gameName);

    void newGame(String gameName);

    String[] getRoomsInfo();

    String[] getItemsInfo();
    
    String[] getRoomPositionInfo();

    String[] getSpecialItemsInfo();
    
    List<String[]> getExitInfo();
    
    String[] getPlayerInfo();
    
    String[] getSaboteurInfo();

    String[] getHelperInfo();

    String[] getTimeHolderInfo();

    LinkedHashMap<String, Integer> getHighscore();
    
    int getRoomsRepaired ();
}
