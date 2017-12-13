/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * A class for storing logs that can be saved as txt files when the execution ends.
 * @author Invenblocker
 */
public class SystemLog
{
    private final static ArrayList<SystemLog> SYSTEM_LOGS = new ArrayList();
    private final static ArrayList<String> GLOBAL_LOG = new ArrayList();
    private final static SystemLog ERROR_LOG = new SystemLog("Error Log");
    private final static SystemLog ACTION_LOG = new SystemLog("Action Log");
    private final static String LOG_PATH = "Logs/";
    
    private final ArrayList<String> LOG;
    private final String NAME;
    private final SystemLog PARENT_LOG;
    
    /**
     * Creates a SystemLog referring to the GLOBAL_LOG as its parent.
     * @author Invenblocker
     * @param name The name of the log.
     */
    public SystemLog(String name)
    {
        this.NAME = name;
        this.LOG = new ArrayList();
        this.PARENT_LOG = null;
        SYSTEM_LOGS.add(this);
    }
    
    /**
     * Creates a SystemLog with a preference to a parentLog.
     * @author Invenblocker
     * @param name The name of the log.
     * @param parentLog The log to be used as a parent.
     */
    public SystemLog(String name, SystemLog parentLog)
    {
        this.NAME = name;
        this.LOG = new ArrayList();
        this.PARENT_LOG = parentLog;
        SYSTEM_LOGS.add(this);
    }
    
    /**
     * Writes a message to the log instance. Message is forwarded to parent log
     * and continues geting forwarded until it eventually ends up in the
     * GLOBAL_LOG.
     * @author Invenblocker
     * @param message The message(s) to write in the log.
     */
    public void writeToLog(String... message)
    {
        for(int i = 0; i < message.length; i++)
        {
            LOG.add(message[i]);
            writeToParentLog(message[i]);
        }
    }
    
    /**
     * Used by logs to write into their parentLog.
     * @author Invenblocker
     * @param message The message to forward.
     */
    private void writeToParentLog(String message)
    {
        if(PARENT_LOG != null)
        {
            PARENT_LOG.writeToLog(getName() + ": " + message);
        }
        else
        {
            writeToGlobalLog(this.getName(), message);
        }
    }
    
    /**
     * Allows fetching of one of the two static logs through a String parameter.
     * @author Invenblocker
     * @param logName The name of the log in question.
     * @return The log that the program has attempted to fetch.
     */
    public static SystemLog getLog(String logName)
    {
        switch(logName)
        {
            case "Error Log":
                return(ERROR_LOG);
            case "Action Log":
                return(ACTION_LOG);
            default:
                ERROR_LOG.writeToLog("The log \"" + logName + "\" is not a static log in the SystemLog class.");
                return(null);
        }
    }
    
    /**
     * Writes into the GLOBAL_LOG.
     * @author Invenblocker
     * @param caller The name of the caller that writes to the GLOBAL_LOG.
     * @param message The message(s) that should be written in the GLOBAL_LOG.
     */
    public static void writeToGlobalLog(String caller, String... message)
    {
        for(int i = 0; i < message.length; i++)
        {
            GLOBAL_LOG.add(caller + ": " + message[i]);
        }
    }
    
    /**
     * @author Invenblocker
     * @return The name of the Log.
     */
    public String getName()
    {
        return(NAME);
    }
    
    /**
     * Creates an array containing the messages saved in the log.
     * @author Invenblocker
     * @return The messages saved in the log.
     */
    public String[] getLog()
    {
        String[] copy = new String[LOG.size()];
        for(int i = 0; i < copy.length; i++)
        {
            copy[i] = LOG.get(i);
        }
        return(copy);
    }
    
    /**
     * Fetches the log entry at a given index.
     * @author Invenblocker
     * @param index The index an entry should be fetched from.
     * @return The message at the given index.
     */
    public String getLogEntry(int index)
    {
        if(index >= 0 && index < LOG.size())
        {
            return(LOG.get(index));
        }
        else
        {
            ERROR_LOG.writeToLog("The index " + index + " is out of bounds in the log " + getName() + '.');
            return("");
        }
    }
    
    /**
     * Creates an array containing the messages saved in the GLOBAL_LOG.
     * @author Invenblocker
     * @return The messages saved in the GLOBAL_LOG.
     */
    public static String[] getGlobalLog()
    {
        String[] log = new String[GLOBAL_LOG.size()];
        for(int i = 0; i < GLOBAL_LOG.size(); i++)
        {
            log[i] = GLOBAL_LOG.get(i);
        }
        return(log);
    }
    
    /**
     * Fetches the log entry at a given index in the GLOBAL_LOG.
     * @author Invenblocker
     * @param index The index an entry should be fetched from.
     * @return The message at the given index.
     */
    public static String getGlobalLogEntry(int index)
    {
        if(index >= 0 && index < GLOBAL_LOG.size())
        {
            return(GLOBAL_LOG.get(index));
        }
        else
        {
            ERROR_LOG.writeToLog("The index " + index + " is out of bounds in the global log.");
            return("");
        }
    }
    
    /**
     * @author Invenblocker
     * @return The static ERROR_LOG.
     */
    public static SystemLog getErrorLog()
    {
        return(ERROR_LOG);
    }
    
    /**
     * @author Invenblocker
     * @return the static ACTION_LOG.
     */
    public static SystemLog getActionLog()
    {
        return(ACTION_LOG);
    }
    
    /**
     * Prints the contents of GLOBAL_LOG to the console.
     * @author Invenblocker
     */
    public static void printGlobalLog()
    {
        String[] log = getGlobalLog();
        for(int i = 0; i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    /**
     * Prints the entry at a given GLOBAL_LOG index to the console.
     * @author Invenblocker
     * @param index The index of the entry to be printed.
     */
    public static void printGlobalLogIndex(int... index)
    {
        String[] log = getGlobalLog();
        for(int i = 0; i < index.length; i++)
        {
            if(index[i] < log.length && index[i] >= 0)
            {
                System.out.println(log[i]);
            }
        }
    }
    
    /**
     * Prints the contents of GLOBAL_LOG from a given index.
     * @author Invenblocker
     * @param index The index of the starting entry.
     */
    public static void printGlobalLogFrom(int index)
    {
        String[] log = getGlobalLog();
        for(int i = Math.max(0, index); i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    /**
     * Prints the contents of GLOBAL_LOG to (not including) a given index.
     * @author Invenblocker
     * @param index The index of the ending entry.
     */
    public static void printGlobalLogTo(int index)
    {
        String[] log = getGlobalLog();
        for(int i = 0; i < index && i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    /**
     * Prints the contents of GLOBAL_LOG between two given indexes.
     * @author Invenblocker
     * @param a The starting index.
     * @param b The ending index.
     */
    public static void printGlobalLogRange(int a, int b)
    {
        String[] log = getGlobalLog();
        for(int i = Math.max(a, 0); i < b && i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    /**
     * Prints the content of the log to the console.
     * @author Invenblocker
     */
    public void printLog()
    {
        String[] log = getLog();
        for(int i = 0; i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    /**
     * Prints the entry at a given index in the log.
     * @author Invenblocker
     * @param index The index of the entry to print.
     */
    public void printLogIndex(int... index)
    {
        String[] log = getLog();
        for(int i = 0; i < index.length; i++)
        {
            if(index[i] < log.length && index[i] >= 0)
            {
                System.out.println(log[i]);
            }
        }
    }
    
    /**
     * Prints the contents of the log from the given index.
     * @author Invenblocker
     * @param index The starting index.
     */
    public void printLogFrom(int index)
    {
        String[] log = getLog();
        for(int i = Math.max(0, index); i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    /**
     * Prints the contents of the log to (not including) the given index.
     * @author Invenblocker
     * @param index The ending index.
     */
    public void printLogTo(int index)
    {
        String[] log = getLog();
        for(int i = 0; i < index && i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    /**
     * Prints the contents of the log between two given indexes.
     * @author Invenblocker
     * @param a The starting index.
     * @param b The ending index.
     */
    public void printLogRange(int a, int b)
    {
        String[] log = getLog();
        for(int i = Math.max(0, a); i < b && i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    /**
     * Saves the GlobalLog as a .txt file.
     * @author Invenblocker
     * @param name The name of the .txt file (excluding fileType extension).
     */
    public static void saveGlobalLog(String name)
    {
        try
        {
            File globalLog = new File(LOG_PATH + fileNamePurge(name) + ".txt");
            PrintWriter logWriter = new PrintWriter(globalLog);

            String[] log = getGlobalLog();

            for(int i = 0; i < log.length; i++)
            {
                logWriter.println(log[i]);
            }

            logWriter.close();
        }
        catch(FileNotFoundException e)
        {
            ERROR_LOG.writeToLog("Could not save the global log.");
            System.out.println("Could not save the global log." + e.getMessage());
        }
    }
    
    /**
     * Saves the log as a .txt file.
     * @author Invenblocker
     */
    public void saveLog()
    {
        try
        {
            File currentLog = new File(LOG_PATH + fileNamePurge(getLongName()) + ".txt");
            PrintWriter logWriter = new PrintWriter(currentLog);

            String[] log = getLog();

            for(int i = 0; i < log.length; i++)
            {
                logWriter.println(log[i]);
            }

            logWriter.close();
        }
        catch(FileNotFoundException e)
        {
            ERROR_LOG.writeToLog("Could not save the log" + getLongName() + '.');
            System.out.println("Could not save the log" + getLongName() + '.' + e.getMessage());
        }
    }
    
    /**
     * Saves all logs as .txt files.
     * @author Invenblocker
     */
    public static void saveAllLogs()
    {
        System.out.println("SAVING ALL LOGS");
        for(SystemLog log : SYSTEM_LOGS)
        {
            log.saveLog();
        }
        saveGlobalLog("GlobalLog");
    }
    
    /**
     * Finds the long name of a log based on its chain of parent logs leading
     * back to the GLOBAL_LOG.
     * @author Invenblocker
     * @return The long name of a log.
     */
    public String getLongName()
    {
        if(PARENT_LOG != null)
        {
            return(PARENT_LOG.getLongName() + ": " + getName());
        }
        else
        {
            return(getName());
        }
    }
    
    /**
     * Removes all contents from a log.
     * @author Invenblocker
     */
    public void wipeLog()
    {
        LOG.clear();
    }
    
    /**
     * Wipes the GLOBAL_LOG and resets the SYSTEM_LOGS list as well as the
     * ACTION_LOG and ERROR_LOG.
     * @author Invenblocker
     */
    public static void wipeAllLogs()
    {
        SYSTEM_LOGS.clear();
        GLOBAL_LOG.clear();
        ACTION_LOG.wipeLog();
        ERROR_LOG.wipeLog();
        SYSTEM_LOGS.add(ACTION_LOG);
        SYSTEM_LOGS.add(ERROR_LOG);
    }
    
    /**
     * Removes symbols that aren't allowed in file names.
     * @param text The String to purge.
     * @return The string after invalid symbols have been removed.
     */
    private static String fileNamePurge(String text)
    {
        text = text.replace('~', '-');
        text = text.replace("#", "no");
        text = text.replace("&", "and");
        text = text.replace("*", "star");
        text = text.replace('{', '(');
        text = text.replace('}', ')');
        text = text.replace('\\', '-');
        text = text.replace(":", " -");
        text = text.replace('<', '[');
        text = text.replace('>', ']');
        text = text.replace("?", "QuestionMark");
        text = text.replace('/', '-');
        text = text.replace('+', ' ');
        text = text.replace('|', 'I');
        text = text.replace('"', '\'');
        text = text.replace('.', ',');
        
        return(text);
    }
}
