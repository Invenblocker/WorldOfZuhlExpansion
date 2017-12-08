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
import acq.IVisualUpdater;
import java.awt.Point;
import java.util.Map;
import logic.elements.characters.Item;

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
    private ILogFacade logFacade;
    
    public LogFacade()
    {
        game = Game.getInstance();
    }   
    
    @Override
    public void injectData (IDataFacade dataFacade)
    {
        data = dataFacade;
    }

    @Override
    public void injectGUIUpdateMethod(IVisualUpdater caller)
    {
        game.getTimeHolder().addVisualUpdateCaller(caller);
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
    public IItem[] getPlayerItems()
    {
        Item[] playerInventory = game.getPlayer().getInventory();
        IItem[] returnInventory = new IItem[playerInventory.length];
        
        for (int i = 0; i < playerInventory.length; i++)
            returnInventory[i] = playerInventory[i];
        
        return returnInventory;
    }

    @Override
    public Map<String, Point> getRoomPositions() {return game.getRoomPositions();}
    
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
    
    @Override
    public void newGame()
    {
        data.getLoader().newGame("assets/maps/bigRectangle.txt");
        game.setupGame(data.getLoader());
       
    }
    
    @Override
    public void loadGame() 
    {
        data.getLoader().loadGame("assets/maps/saveGame.txt");
        game.setupGame(data.getLoader());
        
    }
    
    @Override
    public void play()
    {
        game.play();
    }
    
    @Override
    public void quit()
    {
        game.endGame();
    }

    @Override
    public Map<String, Integer> getHighScore() 
    {
        return data.getLoader().getHighscore();
    }
    
    
    
    
}
