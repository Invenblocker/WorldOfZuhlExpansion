package logic;

import GUI.GUI;
import database.txtLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import logic.elements.characters.Player;
import logic.elements.characters.Saboteur;
import logic.elements.characters.Item;
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
    
    private final double ALLOWED_ROOMS_DESTROYED_PERCENTAGE = 0.3;
    private double roomsDestroyedPercentage;
    
    private boolean gameFinished;
    
    private Parser parser;
    private HashMap<String, Room>rooms;
    private HashMap<String, Item>items;
    private Player player;
    private Saboteur saboteur;
    private GUI gui;
    private TimeHolder timeholder;
    
    private boolean gameLoaded;
    
        
    /**
     * Creates the game object.
     */
    public Game() 
    {
        gameFinished = false;
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
        
        // Setup GUI
        //player = new Player(rooms.get("Control Room"), 2);
        saboteur = new Saboteur(getRandomRoom(), 0.5, 0.1);
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
        while (!gameFinished) {
            Command command = parser.getCommand();
            gameFinished = gameCommand.processCommand(command);
        }
        
        // Game end
        System.out.println("Thank you for playing.  Goodbye.");
        timer.cancel();
    }
             
    public double getALLOWED_ROOMS_DESTROYED_PERCENTAGE() {return ALLOWED_ROOMS_DESTROYED_PERCENTAGE;}

    public double getRoomsDestroyedPercentage() {return roomsDestroyedPercentage;}
    public void setRoomsDestroyedPercentage(double roomsDestroyedPercentage) {
        this.roomsDestroyedPercentage = roomsDestroyedPercentage;
    }
    
    public boolean isGameFinished () {return gameFinished;}
    public void setGameFinished(boolean value)
    {
        gameFinished = value;
    }
    
    public Parser getParser () {return parser;}
    
    public HashMap<String, Room> getRooms() {return rooms;}
    
    public HashMap<String, Item> getItems() {return items;}
    
    public Player getPlayer() {return player;}
    
    public Saboteur getSaboteur() {return saboteur;}
    
    public TimeHolder getTimeHolder() {return timeholder;}
    
    public GUI getGUI() {return gui;}
    
    private Room getRandomRoom()
    {
        Room randomRoom = null;
        
        Random random = new Random();
        List<String> keys = new ArrayList<>(rooms.keySet());
        
        while(randomRoom != null)
        {
            String randomKey = keys.get(random.nextInt(keys.size()));
            Room tempRandomRoom = rooms.get(randomKey);
            if (!tempRandomRoom.isControlRoom())
                randomRoom = tempRandomRoom;
        }
        
        return randomRoom;
    }
    
    /**
    * Prints a welcome message to the player when the game starts.
    */
    private void printWelcome()
    {
        System.out.println("Welcome to the Slower Than Light!");
        System.out.println("You have finally gotten a job as a spaceship pilot.");
        System.out.println("Your first assignment is to fly a cargo ship filled");
        System.out.println("with important supplies to Earth's base on the moon.");
        System.out.println("However a saboteur has infiltrated the spaceship, and");
        System.out.println("does not plan to let you reach the moon.");
        System.out.println("You have to move around the spaceship to fix his havoc");
        System.out.println("But do not let him run into you, or he will kill you.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        //System.out.println(currentRoom.getLongDescription());
    }
}
