/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

/**
 *
 * @author barth_000
 */
public enum HelperTask
{
    SEARCH("search"), BODYGUARD("bodyuard"), RETURN_TO_DEFAULT("return");
    
    private final String COMMAND_STRING;
    
    HelperTask(String commandString)
    {
        this.COMMAND_STRING = commandString;
    }
    
    public String toString()
    {
        return(COMMAND_STRING);
    }
}
