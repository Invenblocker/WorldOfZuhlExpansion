package logic.user_input;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public enum CommandWord
{
    GO("go"),TAKE("take"), DROP("drop"), REPAIR("repair"), INVESTIGATE("investigate"), QUIT("quit"), HELP("help"), UNKNOWN("?");
    
    private String commandString;
    
    /**
     * A constructor for the CommandWord Enumerator.
     * @param commandString The first word of the command entered by the player.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * Returns a string based on the command word.
     * @return The command word entered into the Commandword upon creation.
     */
    public String toString()
    {
        return commandString;
    }
}
