/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

import logic.SystemLog;

/**
 * A series of tasks that can be used to 
 * 
 * @author Invenblocker & JN97
 */
public enum HelperTask
{
    SEARCH("search"), BODYGUARD("bodyguard"), RETURN_TO_DEFAULT("return");
    
    private final String COMMAND_STRING;
    
    /**
     * @param commandString The name of the command. 
     */
    HelperTask(String commandString)
    {
        this.COMMAND_STRING = commandString;
    }
    
    /**
     * @return The name of the command.
     */
    public String toString()
    {
        return(COMMAND_STRING);
    }
    
    /**
     * Finds a task based on a string.
     * @param task A string describing the task tath should be used.
     * @return The task that corresponds to the entered String.
     */
    public static HelperTask getHelperTask(String task)
    {
        switch (task.toLowerCase())
        {
            case "search":
                return SEARCH;
            case "bodyguard":
                return BODYGUARD;
            case "return": case "stay":
                return RETURN_TO_DEFAULT;
            default:
                SystemLog.getErrorLog().writeToLog("The specified command is not a HelperTask.");
                return RETURN_TO_DEFAULT;
        }
    }
}
