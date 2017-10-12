package logic;

import database.txtLoader;
import java.util.HashMap;
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
    private Parser parser;
    private HashMap<String, Room>rooms;
    private HashMap<String, Item>items;
    private Player player;
    private Saboteur saboteur;
    private TimeHolder timeholder;
    
        
    /**
     * Creates the game object.
     */
    public Game() 
    {
        parser = new Parser();
    }
    
    /**
     * Creates the rooms in the world. Has the list of rooms as well as their
     * exits hard coded in the command. Sets the current room as well.
     */
    public void addGameLoader(txtLoader loader)
    {
        
    }
     /**
     * Starts the game and keeps the player locked in this command's while loop
     * until the game ends.
     */
    public void play() 
    {            
        printWelcome();
        
        GameCommand gameCommand = new GameCommand();
        
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = gameCommand.processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    
    public Parser getParser ()
    {
        return parser;
    }
    
    public HashMap<String, Room> getRooms()
    {
        return rooms;
    }
    public HashMap<String, Item> getItems()
    {
        return items;
    }
    
    public Player getPlayer()
    {
        return player;
    }
    
    public Saboteur getSaboteur()
    {
        return saboteur;
    }
    
    public TimeHolder getTimeHolder()
    {
        return timeholder;
    }
    
     /**
     * Prints a welcome message to the player before the game starts.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        //System.out.println(currentRoom.getLongDescription());
    }
   
    /**
     * Goes to the room specified by the player, if the player has not entered a
     * second word in the command, asks the player for direction and does
     * nothing. If the direction is invalid, tells the player that they cannot
     * go in that direction and does nothing.
     * @param command The command entered by the player
     */
    /*private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }*/

    /**
     * Quits the game if no second has been entered into the game.
     * @param command The command entered by the player
     * @return Returns if the quit command is valid.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }
}
