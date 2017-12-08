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
 *
 * @author Johnn
 */
public class SystemLog
{
    private final static ArrayList<SystemLog> SYSTEM_LOGS = new ArrayList();
    private final static ArrayList<String> GLOBAL_LOG = new ArrayList();
    private final static SystemLog ERROR_LOG = new SystemLog("Error Log");
    private final static SystemLog ACTION_LOG = new SystemLog("Action Log");
    private final static String logPath = "Logs/";
    
    private final ArrayList<String> LOG;
    private final String NAME;
    private final SystemLog PARENT_LOG;
    
    public SystemLog(String name)
    {
        this.NAME = name;
        this.LOG = new ArrayList();
        this.PARENT_LOG = null;
        SYSTEM_LOGS.add(this);
    }
    
    public SystemLog(String name, SystemLog parentLog)
    {
        this.NAME = name;
        this.LOG = new ArrayList();
        this.PARENT_LOG = parentLog;
        SYSTEM_LOGS.add(this);
    }
    
    public void writeToLog(String... message)
    {
        for(int i = 0; i < message.length; i++)
        {
            LOG.add(message[i]);
            writeToParentLog(message[i]);
        }
    }
    
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
    
    public static void writeToGlobalLog(String caller, String... message)
    {
        for(int i = 0; i < message.length; i++)
        {
            GLOBAL_LOG.add(caller + ": " + message[i]);
        }
    }
    
    public String getName()
    {
        return(NAME);
    }
    
    public String[] getLog()
    {
        String[] copy = new String[LOG.size()];
        for(int i = 0; i < copy.length; i++)
        {
            copy[i] = LOG.get(i);
        }
        return(copy);
    }
    
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
    
    public static String[] getGlobalLog()
    {
        String[] log = new String[GLOBAL_LOG.size()];
        for(int i = 0; i < GLOBAL_LOG.size(); i++)
        {
            log[i] = GLOBAL_LOG.get(i);
        }
        return(log);
    }
    
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
    
    public static SystemLog getErrorLog()
    {
        return(ERROR_LOG);
    }
    
    public static SystemLog getActionLog()
    {
        return(ACTION_LOG);
    }
    
    public static void printGlobalLog()
    {
        String[] log = getGlobalLog();
        for(int i = 0; i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
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
    
    public static void printGlobalLogFrom(int index)
    {
        String[] log = getGlobalLog();
        for(int i = Math.max(0, index); i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    public static void printGlobalLogTo(int index)
    {
        String[] log = getGlobalLog();
        for(int i = 0; i < index && i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    public static void printGlobalLogRange(int a, int b)
    {
        String[] log = getGlobalLog();
        for(int i = Math.max(a, 0); i < b && i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    public void printLog()
    {
        String[] log = getLog();
        for(int i = 0; i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
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
    
    public void printLogFrom(int index)
    {
        String[] log = getLog();
        for(int i = Math.max(0, index); i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    public void printLogTo(int index)
    {
        String[] log = getLog();
        for(int i = 0; i < index && i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    public void printLogRange(int a, int b)
    {
        String[] log = getLog();
        for(int i = Math.max(0, a); i < b && i < log.length; i++)
        {
            System.out.println(log[i]);
        }
    }
    
    public static void saveGlobalLog(String name)
    {
        try
        {
            File globalLog = new File(logPath + fileNamePurge(name) + ".txt");
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
    
    public void saveLog()
    {
        try
        {
            File currentLog = new File(logPath + fileNamePurge(getLongName()) + ".txt");
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
    
    public static void saveAllLogs()
    {
        System.out.println("SAVING ALL LOGS");
        for(SystemLog log : SYSTEM_LOGS)
        {
            log.saveLog();
        }
        saveGlobalLog("GlobalLog");
    }
    
    public String getLongName()
    {
        if(PARENT_LOG != null)
        {
            return(PARENT_LOG.getLongName() + " - " + getName());
        }
        else
        {
            return(getName());
        }
    }
    
    public void wipeLog()
    {
        LOG.clear();
    }
    
    public static void wipeAllLogs()
    {
        SYSTEM_LOGS.clear();
        GLOBAL_LOG.clear();
        ACTION_LOG.wipeLog();
        ERROR_LOG.wipeLog();
        SYSTEM_LOGS.add(ACTION_LOG);
        SYSTEM_LOGS.add(ERROR_LOG);
    }
    
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
