package logic;

import GUI.GUI;
import database.txtLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import logic.elements.characters.Helper;
import logic.elements.characters.Player;
import logic.elements.characters.Saboteur;
import logic.elements.characters.Item;
import logic.elements.characters.Tool;
import logic.elements.rooms.ItemRoom;
import logic.elements.rooms.Room;
import logic.processors.GameCommand;
import logic.processors.TimeHolder;
import logic.user_input.Command;
import logic.user_input.CommandWord;
import logic.user_input.Parser;

/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Game 
{
    private static Game instance = null;
    public static Game getInstance()
    {
        if (instance == null) 
        {
            instance = new Game();
        }
        return instance;
    }
    
    private HashMap<String, Room>rooms;
    private HashMap<String, Item>items;
    private HashMap<String, Item>specialItems;
    private LinkedHashMap<String, Integer>highScore;
    
    private GameInfo gameInfo;
    private Parser parser;
    private Player player;
    private Saboteur saboteur;
    private TimeHolder timeHolder;
    private GUI gui;
    
    private boolean gameLoaded;
    
        
    /**
     * Creates the game object.
     */
    public Game() 
    {
        gameLoaded = false;
    }
    
    /**
     * Creates the rooms in the world. Has the list of rooms as well as their
     * exits hard coded in the command. Sets the current room as well.
     * @param loader Supply the game with a loader which has loaded all of
     * the data for the game.
     */
    public void setupGame(txtLoader loader)
    {
        // Load elements
        rooms = loader.getRooms();
        items = loader.getItems();
        player = loader.getPlayer();
        saboteur = loader.getSaboteur();
        Helper helper = loader.getHelper();
        
        // Setup Game elements
        Game.GameSetup gameSetup = new GameSetup();
        gameSetup.addItemsToDefaultRooms(items);
        gameSetup.addRepairItemsToRooms(items, rooms);
        
        Room randomRoom = gameSetup.getRandomSaboteurStartRoom(rooms);
        if (player == null)
            player = new Player(randomRoom, 2);
        if (saboteur == null)
            saboteur = new Saboteur(randomRoom, 0.5, 0.1, 0.15);
        if (helper == null)
            helper = new Helper(randomRoom, "Krunk", 0.1, 0.1);
        
        // Setup GameInfo
        gameInfo = new GameInfo(helper);
        
        // Setup Timer
        if (loader.getTimeHolder() == null)
            timeHolder = new TimeHolder(300, 350);
        else
            timeHolder = loader.getTimeHolder();
        
        timeHolder.setupReferences();
        
        // Setup GUI
        gui = new GUI();
        
        // Game is loaded
        gameLoaded = true;
    }
    
     /**
     * Starts the game and keeps the player locked in this command's while loop
     * until the game ends.
     */
    public void play() 
    {            
        if (!gameLoaded)
        {
            System.out.println("A game has not been loaded");
            return;
        }
        
        // Print welcome message
        gui.printWelcome();
        
        // Setup Timer
        Timer timer = new Timer();
        timer.schedule(timeHolder, 0, 1000);
        
        // Setup user input
        GameCommand gameCommand = new GameCommand();
        parser = new Parser();
        //gameCommand.processCommand(new Command(CommandWord.SAVE, ""));
        
        // Game loop
        try
        {
            while (!gameInfo.isGameFinished())
            {
                Command command = parser.getCommand();
                boolean gameFinished = gameCommand.processCommand(command);
                gameInfo.setGameFinished(gameFinished);
            }
        } 
        catch (RuntimeException e)
        {
            System.out.println("Caught RuntimeException");
            e.printStackTrace();
            SystemLog.saveAllLogs(); /* Save logs no matter what*/
        }
        
        // Game end
        timer.cancel();
        SystemLog.saveAllLogs();
        System.out.println("Thank you for playing.  Goodbye.");
    }
    
    public HashMap<String, Room> getRooms() {return rooms;}
    
    public HashMap<String, Item> getItems() {return items;}

    public HashMap<String, Item> getSpecialItems() {return specialItems;}

    public LinkedHashMap<String, Integer> getHighScore() {return highScore;}
    
    public GameInfo getGameInfo() {;return gameInfo;}
    
    public Parser getParser () {return parser;}
    
    public Player getPlayer() {return player;}
    
    public Saboteur getSaboteur() {return saboteur;}
    
    public TimeHolder getTimeHolder() {return timeHolder;}
    
    public GUI getGUI() {return gui;}
    
    
    private static class GameSetup
    {
        void addItemsToDefaultRooms(HashMap<String, Item> items)
        {
            for (Item item : items.values()) {
                Room defaultRoom = item.getDefaultRoom();
                
                if (defaultRoom != null && defaultRoom instanceof ItemRoom) 
                {
                    ItemRoom defaultRoomAsItemRoom = (ItemRoom)item.getDefaultRoom();
                    defaultRoomAsItemRoom.setItem(item);
                }
            }
        }
    
        void addRepairItemsToRooms(HashMap<String, Item> items, HashMap<String, Room> rooms)
        {
            if (items.isEmpty() || rooms.isEmpty())
                return;
            
            List itemKeys = new ArrayList(items.keySet());
            itemKeys.remove("ducttape");
            Collections.shuffle(itemKeys);                 // shuffler keys
            
            for (String roomKey : rooms.keySet())
            {
                if(rooms.get(roomKey) instanceof ItemRoom )
                {
                    Room room = rooms.get(roomKey);
                    Item item;
                    item = items.get(itemKeys.remove(0));
                    
                    room.addRepairTools((Tool) item);
                    //itemKeys.remove(item.getName());
                }
                
                if (itemKeys.isEmpty()) //Stop assigning items if there is no more
                    return;
            }
        }

        Room getRandomSaboteurStartRoom (HashMap<String, Room> rooms)
        {
            Room randomRoom = null;
        
            Random random = new Random();
            List<String> keys = new ArrayList<>(rooms.keySet());
            Collections.shuffle(keys);
            
            while(randomRoom == null)
            {
                String randomKey = keys.remove(0);
                Room tempRandomRoom = rooms.get(randomKey);
                if (!tempRandomRoom.isControlRoom())
                    randomRoom = tempRandomRoom;
            }
            
            return randomRoom;
        }
    }
}
