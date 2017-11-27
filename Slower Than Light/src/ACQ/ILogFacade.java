package acq;

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

    void injectData(IDataFacade dataFacade);

    void processCommand(String Command);
    
    IItem[] getItemsInCurrentRoom();

    IHelper getHelper();

    IPlayer getPlayer();

    ISaboteur getSaboteur();

    ITimeHolder getTimeHolder();
    
    IGameInfo getGameInfo();
    
}
