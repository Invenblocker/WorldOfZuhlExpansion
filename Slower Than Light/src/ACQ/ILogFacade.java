package acq;

import java.awt.Point;
import java.util.Map;

/**
 *
 * @author Johnn
 */
public interface ILogFacade
{
    void newGame();
    
    void loadGame();
    
    void play();
    
    void quit();
    
    void injectData(IDataFacade dataFacade);
    
    void injectVisualCaller(IVisualUpdater caller);

    void processCommand(String Command);
    
    IItem[] getItemsInCurrentRoom();
    
    IItem[] getPlayerItems();
    
    IItem getRepairItemInCurrentRoom();
    
    Map<String, Point> getRoomPositions();
    
    Map<String, Integer> getHighscore();

    IPlayer getPlayer();

    ISaboteur getSaboteur();

    IHelper getHelper();

    ITimeHolder getTimeHolder();
    
    IGameInfo getGameInfo();
    
    
    
}
