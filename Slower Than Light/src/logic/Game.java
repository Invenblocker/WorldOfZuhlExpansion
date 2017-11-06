package logic;

import GUI.GUI;
import database.txtLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
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
    
    private GameInfo gameInfo;
    private Parser parser;
    private Player player;
    private Saboteur saboteur;
    private Helper helper;
    private TimeHolder timeholder;
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
    public void addGameLoader(txtLoader loader)
    {
        rooms = loader.getRooms();
        items = loader.getItems();
        player = loader.getPlayer();
        
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
        
        // Setup Game elements
        Game.GameSetup gameSetup = new GameSetup();
        gameSetup.addItemsToDefaultRooms(items);
        gameSetup.addRepairItemsToRooms(items, rooms);
        
        this.gameInfo = new GameInfo();
        
        // Setup GUI
        Room randomRoom = gameSetup.getRandomSaboteurStartRoom(rooms);
        saboteur = new Saboteur(randomRoom, 0.5, 0.1);
        gui = new GUI();
        
        // Print welcome message
        printWelcome();
        
        // Setup Timer
        timeholder = new TimeHolder(300);
        Timer timer = new Timer();
        timer.schedule(timeholder, 0, 1000);
        
        // Setup user input
        GameCommand gameCommand = new GameCommand();
        parser = new Parser();
        
        // Game loop
        while (!gameInfo.isGameFinished()) {
            Command command = parser.getCommand();
            boolean gameFinished = gameCommand.processCommand(command);
            gameInfo.setGameFinished(gameFinished);
        }
        
        // Game end
        timer.cancel();
        System.out.println("Thank you for playing.  Goodbye.");
    }
    
    public HashMap<String, Room> getRooms() {return rooms;}
    
    public HashMap<String, Item> getItems() {return items;}

    public HashMap<String, Item> getSpecialItems() {return specialItems;}
    
    public GameInfo getGameInfo() {return gameInfo;}
    
    public Parser getParser () {return parser;}
    
    public Player getPlayer() {return player;}
    
    public Saboteur getSaboteur() {return saboteur;}
    
    public Helper getHelper() {return helper;}
    
    public TimeHolder getTimeHolder() {return timeholder;}
    
    public GUI getGUI() {return gui;}
    
    
    static class GameSetup
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
            Room room = null;
            Item item = null;
            List itemKeys = new ArrayList(items.keySet());
            itemKeys.remove("ducttape");
            Collections.shuffle(itemKeys);                 // shuffler keys
            
            for (String roomKey : rooms.keySet())
            {
                if(rooms.get(roomKey) instanceof ItemRoom )
                {
                    room = rooms.get(roomKey);
                    
                    item = items.get(itemKeys.get(0));
                    
                    room.addRepairTools((Tool) item);
                    itemKeys.remove(item.getName());
                }
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
