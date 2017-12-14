package acq;

import java.util.List;
import java.util.Map;

/**
 *
 * @author mortenskovgaard
 */
public interface IGameInfo {

    double getALLOWED_ROOMS_DESTROYED_PERCENTAGE();
    
    double getDestroyedRoomsPercentage();
    
    List<IRoom> getDestroyedRooms();

    Map<String, Integer> saveHighScore(String playerName);

    Map<String, Integer> getHighScoreMap();
    
    int getScore();

    boolean isGameFinished();
    
}
