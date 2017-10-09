package logic;

import logic.elements.rooms.Room;
import logic.user_input.Command;
import logic.user_input.CommandWord;
import logic.user_input.Parser;

/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Creates the game object.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }
    
    /**
     * Creates the rooms in the world. Has the list of rooms as well as their
     * exits hard coded in the command. Sets the current room as well.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;
      
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;
    }
    
    /**
     * Starts the game and keeps the player locked in this command's while loop
     * until the game ends.
     */
    public void play() 
    {            
        printWelcome();

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
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
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Processes the command entered by the player running its function and
     * checking if the game should end.
     * @param command The command entered by the player.
     * @return A boolean that states if the game should end.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (null != commandWord) switch (commandWord) {
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            default:
                System.out.println("I don't know what you mean...\nType \"help\" for a list of commands.");
                return false;
        }
        else
        {
            System.out.println("Please write in a command before hitting enter.");
        }
        return wantToQuit;
    }
    
    /**
     * Prints a short description of the game and then a list of commands.
     */
    private void printHelp() 
    {
        System.out.println("You have finally gotten a job as a spaceship pilot.");
        System.out.println("Your first assignment is to fly a cargo ship filled");
        System.out.println("with important supplies to Earth's base on the moon.");
        System.out.println("However a saboteur has infiltrated the spaceship, and");
        System.out.println("does not plan to let you reach the moon.");
        System.out.println("You have to move around the spaceship to fix his havoc");
        System.out.println("But do not let him run into you, or he will kill you.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    /**
     * Goes to the room specified by the player, if the player has not entered a
     * second word in the command, asks the player for direction and does
     * nothing. If the direction is invalid, tells the player that they cannot
     * go in that direction and does nothing.
     * @param command The command entered by the player
     */
    private void goRoom(Command command) 
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
    }

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
