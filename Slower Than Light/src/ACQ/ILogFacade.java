package acq;

import java.awt.Point;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Johnn
 */
public interface ILogFacade {

    void play();
    
    void quit();
    
    void loadGame();
    
    void newGame();
    
    void injectData(IDataFacade dataFacade);
    
    void injectGUIUpdateMethod(IVisualUpdater caller);

    void processCommand(String Command);
    
    IItem[] getItemsInCurrentRoom();
    
    IItem[] getPlayerItems();
    
    Map<String, Point> getRoomPositions();

    IHelper getHelper();

    IPlayer getPlayer();

    ISaboteur getSaboteur();

    ITimeHolder getTimeHolder();
    
    IGameInfo getGameInfo();
    
}
