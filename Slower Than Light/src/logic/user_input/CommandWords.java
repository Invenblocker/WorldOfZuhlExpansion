package logic.user_input;

import java.util.HashMap;

/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class CommandWords
{
    private HashMap<String, CommandWord> validCommands;
    
    /**
     * A constructor that creates an object of the CommandWords type without
     * taking in any arguments.
     */
    public CommandWords()
    {
        validCommands = new HashMap<String, CommandWord>();
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }
    
    /**
     * Takes in the command entered by the player and checks if it is valid.
     * Returns the command if valid or UNKNOWN if not.
     * @param commandWord The command entered by the player.
     * @return A command word that is either a valid command or UNKNOWN;
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        }
        else {
            return CommandWord.UNKNOWN;
        }
    }
    
    /**
     * Checks if the entered command is valid.
     * @param aString A command that has to be checked.
     * @return A boolean that states if the entered command is a valid one.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }
    
    /**
     * Prints a list of all valid commands.
     */
    public void showAll() 
    {
        for(String command : validCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
