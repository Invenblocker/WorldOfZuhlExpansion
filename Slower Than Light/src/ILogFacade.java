/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import acq.IHelper;

/**
 *
 * @author Johnn
 */
public interface ILogFacade {

    IHelper getHelper();

    IPlayer getPlayer();

    ISaboteur getSaboteur();

    ITimeholder getTimeholder();

    void injectData(IDataFacade dataFacade);

    void processCommand(String Command);
    
}
