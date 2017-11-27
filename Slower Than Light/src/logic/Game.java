package logic;

import GUI.GUIController;
import acq.ILoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import logic.elements.characters.Helper;
import logic.elements.characters.HelperTask;
import logic.elements.characters.Player;
import logic.elements.characters.Saboteur;
import logic.elements.characters.Item;
import logic.elements.characters.Tool;
import logic.elements.rooms.ItemRoom;
import logic.elements.rooms.Room;
import logic.processors.GameCommand;
import logic.processors.TimeHolder;
import logic.user_input.Command;
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
    private TimeHolder timeHolder;
    private GameCommand gameCommand;
    private GUIController guiController;
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
    public void setupGame(ILoader loader)
    {
        // Setup Game elements
        rooms = loader.getRooms();
        items = loader.getItems();
        specialItems = loader.getSpecialItems();
        
        Game.StringConverter sc = new StringConverter();
        sc.initializePlayer(loader.getPlayerInfo(), rooms, items, specialItems);
        sc.initializeSaboteur(loader.getSaboteurInfo(), rooms);
        sc.initializeHelper(loader.getHelperInfo(), rooms);
        sc.initializeTimeHolder(loader.getTimeHolderInfo());
        
        player = sc.playerSC;
        saboteur = sc.saboteurSC;
        helper = sc.helperSC;
        timeHolder = sc.timeHolderSC;
        
        Game.GameSetup gameSetup = new GameSetup();
        gameSetup.addItemsToDefaultRooms(items);
        gameSetup.addRepairItemsToRooms(items, rooms);
        
        Room randomRoom = gameSetup.getRandomSaboteurStartRoom(rooms);
        if (player == null)
            player = new Player(rooms.get("controlRoom"), 2);
        if (saboteur == null)
            saboteur = new Saboteur(randomRoom, 0.5, 0.1, 0.15);
        if (helper == null)
            helper = new Helper(randomRoom, "Krunk", 0.1, 0.1);
        
        // Setup GameInfo
        gameInfo = new GameInfo(helper);
        
        // Setup Timer
        /*if (loader.getTimeHolder() == null)
            timeHolder = new TimeHolder(300, 350);
        else
            timeHolder = loader.getTimeHolder();*/
        timeHolder.setupReferences();
        
        // Setup GameCommand
        gameCommand = new GameCommand();
        
        // Setup GUI
        guiController = new GUIController();
        
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
        guiController.printWelcome();
        
        // Setup Timer
        Timer timer = new Timer();
        timer.schedule(timeHolder, 0, 1000);
        
        // Setup user input
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
    
    public GameInfo getGameInfo() {return gameInfo;}
    
    public Parser getParser () {return parser;}
    
    public Player getPlayer() {return player;}
    
    public Saboteur getSaboteur() {return saboteur;}
    
    public Helper getHelper() {return helper;}
    
    public TimeHolder getTimeHolder() {return timeHolder;}
    
    public GameCommand getGameCommand() {return gameCommand;}
    
    public GUIController getGUI() {return guiController;}
    
    
    private class GameSetup
    {
        private void addItemsToDefaultRooms(HashMap<String, Item> items)
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
    
        private void addRepairItemsToRooms(HashMap<String, Item> items, HashMap<String, Room> rooms)
        {
            if (items.isEmpty() || rooms.isEmpty())
                return;
            
            List itemKeys = new ArrayList(items.keySet());
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

        private Room getRandomSaboteurStartRoom (HashMap<String, Room> rooms)
        {
            Room randomRoom = null;
        
            Random random = new Random();
            List<String> keys = new ArrayList<>(rooms.keySet());
            Collections.shuffle(keys);
            
            if (keys.isEmpty())
                return randomRoom; // Return null
            
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
    
    private class StringConverter
    {
        private Player playerSC;
        private Saboteur saboteurSC;
        private Helper helperSC;
        private TimeHolder timeHolderSC;
        
        private void initializePlayer(String[] words, HashMap<String, Room> _rooms, HashMap<String, Item> _items, HashMap<String, Item> _specialItems)
        {
            Room room = _rooms.get(words[1]);    //Sets room reference equal to index 1 in our hashmap, which is a room.

            playerSC = new Player(room, Integer.parseInt(words[2]));
            int i = 3;         //index for item in txtfile
            
            if(_items.containsKey(words[i]))           //checks if txtfile contains same key in items hashmap
                playerSC.addItem(_items.get(words[i])); //adds item to player inventory
            else if (_specialItems.containsKey(words[i]))  //checks if txtfile contains same key in specialItems hashmap
                playerSC.addItem(_specialItems.get(words[i]));    //adds specialItem to player inventory
            
            i++;
        }
      
        private void initializeSaboteur(String[] words, HashMap<String, Room> _rooms)
        {
            Room room = _rooms.get(words[1]);
            saboteurSC = new Saboteur(room, Double.parseDouble(words[2]), Double.parseDouble(words[3]), Double.parseDouble(words[4]));
            saboteurSC.setChanceOfSabotage(Double.parseDouble(words[5]));
            saboteurSC.addStunCountdown(Integer.parseInt(words[6]));
        }

        private void initializeHelper(String[] words, HashMap<String, Room> _rooms)
        {
            Room room = _rooms.get(words[1]);     //Helpers room in txt file
            helperSC = new Helper(room, words[2], Double.parseDouble(words[4]), Double.parseDouble(words[5])); //[3]chance of discovery growht og [4] er
            helperSC.setChanceOfDiscovery(Double.parseDouble(words[6]));
            helperSC.setTask(HelperTask.getHelperTask(words[3]));
        }
        
        private void initializeTimeHolder(String[] words)
        {
            timeHolderSC = new TimeHolder(Double.parseDouble(words[1]), Double.parseDouble(words[2]));
            timeHolderSC.setHelperCountdown(Integer.parseInt(words[3]));
            timeHolderSC.setSaboteurCountdown(Integer.parseInt(words[4]));
        }
    }
}
