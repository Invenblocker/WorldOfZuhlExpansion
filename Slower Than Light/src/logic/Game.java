package logic;

import GUI.GUI;
import acq.ILoader;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import logic.elements.characters.Helper;
import logic.elements.characters.HelperTask;
import logic.elements.characters.Player;
import logic.elements.characters.Saboteur;
import logic.elements.characters.Item;
import logic.elements.characters.Tool;
import logic.elements.rooms.ControlRoom;
import logic.elements.rooms.Exit;
import logic.elements.rooms.ItemRoom;
import logic.elements.rooms.Room;
import logic.elements.rooms.WorkshopRoom;
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
            instance = new Game();
        
        return instance;
    }
    
    private Map<String, Point> roomPositions;
    
    private Map<String, Room>rooms;
    private Map<String, Item>items;
    private Map<String, Item>specialItems;
    
    private GameInfo gameInfo;
    private Parser parser;
    private Player player;
    private Saboteur saboteur;
    private Helper helper;
    private TimeHolder timeHolder;
    private GameCommand gameCommand;
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
     * @param loader The loader that contains all the data for the game.
     */
    public void setupGame(ILoader loader)
    {
        // Setup Game elements
        Game.StringConverter sc = new StringConverter();
        sc.initializeMiniMap(loader.getRoomPositionInfo());
        sc.initializeRooms(loader.getRoomsInfo());
        sc.initializeItems(loader.getItemsInfo());
        sc.initializeSpecialItems(loader.getSpecialItemsInfo());
        for (String[] exitString : loader.getExitInfo())
            sc.initializeExit(exitString);
        sc.initializePlayer(loader.getPlayerInfo());
        sc.initializeSaboteur(loader.getSaboteurInfo());
        sc.initializeHelper(loader.getHelperInfo());
        sc.initializeTimeHolder(loader.getTimeHolderInfo());
        
        roomPositions = sc.roomPositionsSC;
        rooms = sc.roomsSC;
        items = sc.itemsSC;
        specialItems = sc.specialItemsSC;
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
        if (sc.timeHolderSC == null)
            timeHolder = new TimeHolder(300, 350);
        timeHolder.setupReferences();
        
        // Setup GameCommand
        gameCommand = new GameCommand();
        
        // Setup GUI
        gui = GUI.getInstance();
        
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
            String msg = "The game has not been loaded";
            System.out.println(msg);
            SystemLog.getErrorLog().writeToLog(msg);
            SystemLog.saveAllLogs();
            return;
        }
        
        // Print welcome message
        gui.printWelcome();
        
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

    public Map<String, Point> getRoomPositions() {return roomPositions;}
    
    public Map<String, Room> getRooms() {return rooms;}
    
    public Map<String, Item> getItems() {return items;}

    public Map<String, Item> getSpecialItems() {return specialItems;}
    
    public GameInfo getGameInfo() {return gameInfo;}
    
    public Parser getParser () {return parser;}
    
    public Player getPlayer() {return player;}
    
    public Saboteur getSaboteur() {return saboteur;}
    
    public Helper getHelper() {return helper;}
    
    public TimeHolder getTimeHolder() {return timeHolder;}
    
    public GameCommand getGameCommand() {return gameCommand;}
    
    public GUI getGUI() {return gui;}
    
    
    private class GameSetup
    {
        private void addItemsToDefaultRooms(Map<String, Item> items)
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
    
        private void addRepairItemsToRooms(Map<String, Item> items, Map<String, Room> rooms)
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

        private Room getRandomSaboteurStartRoom (Map<String, Room> rooms)
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
        private Map<String, Point> roomPositionsSC;
        private String playerPositionSC;
        private String saboteurPositionSC;
        
        
        private Map<String, Room> roomsSC;
        private Map<String, Item> itemsSC;
        private Map<String, Item> specialItemsSC;
        private Player playerSC;
        private Saboteur saboteurSC;
        private Helper helperSC;
        private TimeHolder timeHolderSC;
        
        private StringConverter()
        {
            roomPositionsSC = new HashMap<>();
            
            roomsSC = new HashMap<>();
            itemsSC = new HashMap<>();
            specialItemsSC = new HashMap<>();
        }
        
        //Method inserts room to our hasmap rooms
        //Private as the method is only used and accessed in the txtLoader class
        private void initializeRooms (String[] words)
        {
            int i = 1;                 //index for room in our txt file            
            int j = 2;                 //index for boolean in our txt file    
            int k = 3;
            Room room;
            
            while (j < words.length)    //As long j is less than array lenght put room
            {
                switch (words[j]) {
                    case "ItemRoom":
                        roomsSC.put(words[i], new ItemRoom(words[i]));
                        room = roomsSC.get(words[i]);
                        room.setOperating(Boolean.parseBoolean(words[k]));
                        break;
                    case "WorkshopRoom":
                        roomsSC.put(words[i], new WorkshopRoom(words[i]));
                        room = roomsSC.get(words[i]);
                        room.setOperating(Boolean.parseBoolean(words[k]));
                        break;
                    default:
                        roomsSC.put(words[i], new ControlRoom(words[i]));
                        room = roomsSC.get(words[i]);
                        room.setOperating(Boolean.parseBoolean(words[k]));
                        break;
                }
                
                i += 3;         //Jumps to room index in our txt
                j += 3;         //jumps to next boolean in txt
                k += 3;
            }
        }
        
        private void initializeMiniMap (String[] words) {
            
            int i = 3;                                          
            int j = 4;
            int k = 5;
            
            playerPositionSC = words[1];
            saboteurPositionSC = words[2];
            
            while (k < words.length)
            {
               roomPositionsSC.put(words[i], new Point (Integer.parseInt(words[j]), Integer.parseInt(words[k])));

               i += 3;
               j += 3;
               k += 3;  
            }
        }
        
        private void initializeItems (String[] words){
            int i = 1;
            int j = 2;
            int k = 3;

            while (k < words.length)
            {
                for (String key : roomsSC.keySet())
                    if (key.equals(words[k]))
                        if(words[i].equals("Tool"))
                            itemsSC.put(words[j], new Tool(words[j], (ItemRoom) roomsSC.get(key)));

                i += 3;    
                j += 3;
                k += 3;
            }
        }
        
        private void initializeSpecialItems (String[] words)   //Adds specialItem to HashMap specialItems
        {
            int i = 1;      //index for specialItems in txtfile
            int j = 2;

            while (j < words.length) //As long as i is less then the length of do ->  speicialItems.put
            {        
                if (words[i].equals("Tool"))
                    specialItemsSC.put(words[j],new Tool(words[j]));
                else if (words[i].equals("Item"))
                    specialItemsSC.put(words[j],new Item(words[j]));
                
                i += 2;
                j += 2;
            }
        }
        
        private void initializeExit(String[] words)
        {
            int i = 1;
            int j = 2;
            int k = 3;
            Room room = null;
            Room room2 = null;
            Exit exit = null;

            while (k < words.length)
            {                                           // tjekker at vi ikke overskrider arrayet
                for (String key : roomsSC.keySet())       // tjekker alle keys i vores hashmap rooms (workshop, controlroom, pub, lab)
                    if (key.equals(words[0]))           // tjekker om plads 0 (et rum) er det rum vi kigger på.
                        room = roomsSC.get(key);          // sætter rum = det rum vi vil arbejde med.
                
                for (String key : roomsSC.keySet()){      // tjekker alle rum i hashmappet room
                    if (key.equals(words[k])){          // finder det andet rum som skal sættes som exit til room.
                        room2 = roomsSC.get(key);         // sætter rummet til room2.
                        exit = new Exit(room, room2);
                        exit.setOperating(Boolean.parseBoolean(words[j]));
                        room.setExit(words[i], exit);  // sætter exit med plads i (en direction) og et room.
                    }
                }
                
                i += 3;
                j += 3;
                k += 3;
            }
        }
        
        private void initializePlayer(String[] words)
        {
            Room room = roomsSC.get(words[1]);    //Sets room reference equal to index 1 in our hashmap, which is a room.

            playerSC = new Player(room, Integer.parseInt(words[2]));
            int i = 3;         //index for item in txtfile
            
            if(itemsSC.containsKey(words[i]))           //checks if txtfile contains same key in items hashmap
                playerSC.addItem(itemsSC.get(words[i])); //adds item to player inventory
            else if (specialItemsSC.containsKey(words[i]))  //checks if txtfile contains same key in specialItems hashmap
                playerSC.addItem(specialItemsSC.get(words[i]));    //adds specialItem to player inventory
            
            i++;
        }
      
        private void initializeSaboteur(String[] words)
        {
            Room room = roomsSC.get(words[1]);
            saboteurSC = new Saboteur(room, Double.parseDouble(words[2]), Double.parseDouble(words[3]), Double.parseDouble(words[4]));
            saboteurSC.setChanceOfSabotage(Double.parseDouble(words[5]));
            saboteurSC.addStunCountdown(Integer.parseInt(words[6]));
        }

        private void initializeHelper(String[] words)
        {
            Room room = roomsSC.get(words[1]);     //Helpers room in txt file
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
