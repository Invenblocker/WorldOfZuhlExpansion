package logic.user_input;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Parser 
{
    private CommandWords commands;  //Creates an object to keep track of commands.
    private Scanner reader;

    
    /**
     * Constructor for object of the Parser type.
     * Takes no arguments to create the object.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }
    
    /**
     * A method that reads a two words long command entered by the player.
     * Takes no argument to read the command.
     * @return Returns an object of the Command type containing the command entered by the player.
     */
    public Command getCommand() 
    {
        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print("> ");

        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();
            }
        }

        return new Command(commands.getCommandWord(word1), word2);
    }
    
    /**
     * Shows a list of valid commands.
     */
    public void showCommands()
    {
        commands.showAll();
    }
    public Command processInput(String input)
    {
        String word1 = null;
        String word2 = null;
               
        Scanner tokenizer = new Scanner(input);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();
            }
        }

        return new Command(commands.getCommandWord(word1), word2);
    }
    
    
}
