package logic;

import GUI.GUI;
import database.txtLoader;
import java.util.HashMap;
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
    
    private Parser parser;
    private HashMap<String, Room>rooms;
    private HashMap<String, Item>items;
    private Player player;
    private Saboteur saboteur;
    private TimeHolder timeholder;
    private boolean gameFinished;
    
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
        
        timeholder = new TimeHolder(300);
        Timer timer = new Timer();
        timer.schedule(timeholder, 0, 1000);
        
        printWelcome();
        
        GameCommand gameCommand = new GameCommand();
        parser = new Parser();
        
        while (!gameFinished) {
            Command command = parser.getCommand();
            gameFinished = gameCommand.processCommand(command);
        }
        System.out.println("Thank you for playing.  Goodbye.");
        timer.cancel();
    }
             
    public double getALLOWED_ROOMS_DESTROYED_PERCENTAGE() {
        return ALLOWED_ROOMS_DESTROYED_PERCENTAGE;
    }
    

    public double getRoomsDestroyedPercentage() {
        return roomsDestroyedPercentage;
    }

    public void setRoomsDestroyedPercentage(double roomsDestroyedPercentage) {
        this.roomsDestroyedPercentage = roomsDestroyedPercentage;
    }
    
    public Parser getParser () {return parser;}
    
    public HashMap<String, Room> getRooms() {return rooms;}
    
    public HashMap<String, Item> getItems() {return items;}
    
    public Player getPlayer() {return player;}
    
    public Saboteur getSaboteur() {return saboteur;}
    
    public TimeHolder getTimeHolder() {return timeholder;}
    
    public void setGameFinished(boolean value)
    {
        gameFinished = value;
    }
    
    public boolean isGameFinished () {
        return gameFinished;
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

    public GUI getGUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
