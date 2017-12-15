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
    // used for referencing (Singleton)
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
    
    /**
     * Makes a refrence to where out newGame fill is.
     * And tells setupGame how to get it when you load a new game.
     */
    @Override
    public void newGame()
    {
        data.getLoader().newGame("assets/maps/bigRectangle.txt");
        game.setupGame(data.getLoader());
       
    }
    /**
     * Makes a refrence to where out saveGame fill is.
     * And tells setupGame how to get it when you load a saved game.
     */
    @Override
    public void loadGame() 
    {
        data.getLoader().loadGame("assets/maps/saveGame.txt");
        game.setupGame(data.getLoader());
    }
    
    /**
     * makes a referece to the play method
     */
    @Override
    public void play()
    {
        game.play();
    }
    
    /**
     * make a refrence to the endGame method
     */
    @Override
    public void quit()
    {
        game.endGame();
    }
    
    /**
     * gets a refrence of the objekt datafacede
     * @param dataFacade 
     */
    @Override
    public void injectData (IDataFacade dataFacade)
    {
        data = dataFacade;
    }
    
    /**
     * makes a refrence to the addVisualUpdateCaller method
     * @param caller 
     */
    @Override
    public void injectVisualCaller(IVisualUpdater caller)
    {
        game.getTimeHolder().addVisualCaller(caller);
    }
    
    /**
     * Makes a refrence to our processCommand.
     * @param Command 
     */
    @Override
    public void processCommand (String Command)
    {
        Parser process = Game.getInstance().getParser();
        Command command = process.processInput(Command);
        Game.getInstance().getGameCommand().processCommand(command);
    }
    
    /**
     * 
     * @return a list with items the the current room
     */
    @Override
    public IItem[] getItemsInCurrentRoom()
    {
        return game.getGameCommand().getItemsInCurrentRoom();
    }
    
    /**
     * First we get the player's inventory. The we make a new list with the 
     * player's inventory. 
     * @return the players inventory
     */
    @Override
    public IItem[] getPlayerItems()
    {
        Item[] playerInventory = game.getPlayer().getInventory();
        IItem[] returnInventory = new IItem[playerInventory.length];
        
        for (int i = 0; i < playerInventory.length; i++)
            returnInventory[i] = playerInventory[i];
        
        return returnInventory;
    }
    
    /**
     * 
     * @return The item which the player needs to repair the room
     */
    @Override
    public IItem getRepairItemInCurrentRoom()
    {
        return game.getPlayer().getCurrentRoom().getRepairTools().get(0);
    }
    
    /**
     * 
     * @return a reference to the roomPosition.
     */
    @Override
    public Map<String, Point> getRoomPositions() {return game.getRoomPositions();}
    
    /**
     * 
     * @return a refrence to the player. 
     */
    @Override
    public IPlayer getPlayer() {return game.getPlayer();}
    
    /**
     * 
     * @return a refrence to the Saboteur.
     */
    @Override
    public ISaboteur getSaboteur() {return game.getSaboteur();}
    
    /**
     * 
     * @return a refrence to the Helper
     */
    @Override
    public IHelper getHelper() {return game.getHelper();}
    
    /**
     * 
     * @return a refrence to timeHolder
     */
    @Override
    public ITimeHolder getTimeHolder() {return game.getTimeHolder();}
    
    /**
     * 
     * @return a refrence to gameInfo.
     */
    @Override
    public IGameInfo getGameInfo() {return game.getGameInfo();}
    
    /**
     * 
     * @return a refrence to our dataFacede. 
     */
    public IDataFacade getDataFacade() {return data;}
    
    /**
     * 
     * @return a map with highscore
     */
    @Override
    public Map<String, Integer> getHighscore() {return data.getLoader().getHighscore();}
    
}
