/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.ArrayList;

/**
 *
 * @author Johnn
 */
public class SystemLog
{
    private static ArrayList<String> globalLog = new ArrayList();
    private ArrayList<String> log;
    private String name;
    
    private static SystemLog errorLog = new SystemLog("Error Log");
    private static SystemLog actionLog = new SystemLog("Action Log");

    public SystemLog(String name)
    {
        this.name = name;
        log = new ArrayList();
    }
    
    public void writeToLog(String... message)
    {
        for(int i = 0; i < message.length; i++)
        {
            log.add(message[i]);
        }
        
        writeToGlobalLog(message);
    }
    
    private void writeToGlobalLog(String... message)
    {
        for(int i = 0; i < message.length; i++)
        {
            globalLog.add(getName() + ": " + message[i]);
        }
    }
    
    public static SystemLog getLog(String logName)
    {
        switch(logName)
        {
            case "Error Log":
                return(errorLog);
            case "Action Log":
                return(actionLog);
            default:
                errorLog.writeToLog("The log \"" + logName + "\" is not a static log in the SystemLog class.");
                return(null);
        }
    }
    
    public static void writeToGlobalLog(String caller, String... message)
    {
        for(int i = 0; i < message.length; i++)
        {
            globalLog.add(caller + ": " + message[i]);
        }
    }
    
    public String getName()
    {
        return(name);
    }
    
    public String[] getLog()
    {
        String[] copy = new String[log.size()];
        for(int i = 0; i < copy.length; i++)
        {
            copy[i] = log.get(i);
        }
        return(copy);
    }
    
    public String getLogEntry(int index)
    {
        if(index >= 0 && index < log.size())
        {
            return(log.get(index));
        }
        else
        {
            errorLog.writeToLog("The index " + index + " is out of bounds in the log " + getName() + '.');
            return("");
        }
    }
    
    public static String[] getGlobalLog()
    {
        String[] log = new String[globalLog.size()];
        for(int i = 0; i < globalLog.size(); i++)
        {
            log[i] = globalLog.get(i);
        }
        return(log);
    }
    
    public static String getGlobalLogEntry(int index)
    {
        if(index >= 0 && index < globalLog.size())
        {
            return(globalLog.get(index));
        }
        else
        {
            errorLog.writeToLog("The index " + index + " is out of bounds in the global log.");
            return("");
        }
    }
    
    public static SystemLog getErrorLog()
    {
        return(errorLog);
    }
    
    public static SystemLog getActionLog()
    {
        return(actionLog);
    }
}
