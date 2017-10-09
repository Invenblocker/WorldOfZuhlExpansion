package logic.user_input;

/** 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class Command
{
    private CommandWord commandWord;
    private String secondWord;
    
    /**
     * A constructor that creates an object of the Command type based on a one
     * or two words long command entered by the player.
     * @param commandWord The first word of the command.
     * @param secondWord The potential second word of the command.
     */
    public Command(CommandWord commandWord, String secondWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }
    
    /**
     * Returns the first word of the command as a CommandWord
     * @return The first word of the command stored in the object of the Command type.
     */
    public CommandWord getCommandWord()
    {
        return commandWord;
    }
    
    /**
     * Returns the potential second word of the command entered by the player
     * should the command defined by the first word need it.
     * @return The second word of the command stored in the Command object.
     */
    public String getSecondWord()
    {
        return secondWord;
    }
    
    /**
     * Checks if the first word of the command is unknown.
     * @return A boolean that tells the system if the command is unknown.
     */
    public boolean isUnknown()
    {
        return (commandWord == CommandWord.UNKNOWN);
    }
    
    /**
     * Checks if the command has a second word.
     * @return Returns true if the command has been entered with a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}

