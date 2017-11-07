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
    static ArrayList<String> globalLog = new ArrayList();
    ArrayList<String> log;
    String name;
    
    private static SystemLog errorLog = new SystemLog("Error Log");
    private static SystemLog actionLog = new SystemLog("Action Log");

    public SystemLog(String name)
    {
        this.name = name;
        log = new ArrayList();
    }
    
    
}
