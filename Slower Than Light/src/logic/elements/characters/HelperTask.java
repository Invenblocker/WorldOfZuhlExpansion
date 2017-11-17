/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

import logic.SystemLog;

/**
 *
 * @author barth_000
 */
public enum HelperTask
{
    SEARCH("search"), BODYGUARD("bodyguard"), RETURN_TO_DEFAULT("return");
    
    private final String COMMAND_STRING;
    
    HelperTask(String commandString)
    {
        this.COMMAND_STRING = commandString;
    }
    
    public String toString()
    {
        return(COMMAND_STRING);
    }
    
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
