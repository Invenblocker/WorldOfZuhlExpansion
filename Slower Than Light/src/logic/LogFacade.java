/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import acq.IDataFacade;
import acq.IGameInfo;
import acq.ILogFacade;
import acq.IHelper;
import acq.IItem;
import acq.IPlayer;
import acq.ISaboteur;
import acq.ITimeHolder;
import logic.user_input.Command;
import logic.user_input.Parser;

/**
 *
 * @author Johnn
 */
public class LogFacade implements ILogFacade 
{
    private static LogFacade instance;
    public static LogFacade getInstance()
    {
        if (instance == null)
            instance = new LogFacade();
        
        return instance;
    }
    
    private IDataFacade data;
    private Game game;
    
    public LogFacade()
    {
        game = Game.getInstance();
    }   
    
    @Override
    public void injectData (IDataFacade dataFacade)
    {
        data = dataFacade;
        game.setupGame(data.getLoader());
    }
    
    @Override
    public void processCommand (String Command)
    {
        Parser process = Game.getInstance().getParser();
        Command command = process.processInput(Command);
        Game.getInstance().getGameCommand().processCommand(command);
    }

    @Override
    public IItem[] getItemsInCurrentRoom()
    {
        return game.getGameCommand().getItemsInCurrentRoomItems();
    }
    
    @Override
    public IPlayer getPlayer() {return game.getPlayer();}
    
    @Override
    public ISaboteur getSaboteur() {return game.getSaboteur();}
    
    @Override
    public IHelper getHelper() {return game.getHelper();}
    
    @Override
    public ITimeHolder getTimeHolder() {return game.getTimeHolder();} 
    
    @Override
    public IGameInfo getGameInfo() {return game.getGameInfo();}
    
    public IDataFacade getDataFacade() {return data;}
    
}
