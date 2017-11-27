/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import acq.IGUI;
import acq.IItem;
import acq.ILogFacade;
import acq.IRoom;
import acq.IVisualUpdateCaller;
import java.util.Map;
import logic.Game;

/**
 *
 * @author Erik
 */
public class GUI implements IGUI, IVisualUpdateCaller
{
    private static GUI instance = null;
    public static GUI getInstance()
    {
        if (instance == null)
            instance = new GUI();
        
        return instance;
    }
    
    private ILogFacade logFacade;
    
    private MiniMap minimap;
    private Log log;
    
    public GUI()
    {
        //this.minimap = new MiniMap();       //Creates new minimap object
        this.log = new Log(5);
    }
    
    @Override
    public void injectLogic(ILogFacade _logFacade)
    {
        logFacade = _logFacade;
        logFacade.injectGUIUpdateMethod(this);
    }

    @Override
    public void updateWithTimer()
    {
        writeToLog("Updated timer");
    }
  
    public void updateMinimap(IRoom saboteurRoom, IRoom[] destroyedRooms) { //updates saboteur position, calls update in MiniMap class.
        //minimap.update(saboteurRoom, destroyedRooms);
        
    }
  
    public void updateRoom(IRoom room)       //updates player position to minimap   
    {
        //minimap.updatePlayerPosition(room);
        writeToLog("You moved to " + logFacade.getPlayer().getCurrentRoom().getName());
    }
  
    public void updateInventory(IItem[] inventory)
    {
        System.out.println(inventory);
    }
  
    public void investigate(IRoom room)
    {
        
    }
  
    /**
      * Prints a short description of the game and then a list of commands.
      */
    public void printHelp()
    {
        System.out.println("YOU BE FUCKED");
        System.out.println();
        System.out.println("Your command words are:");
        Game.getInstance().getParser().showCommands();
    }
  
    public void printWelcome() {
        
    }
    public void prinInventory(IItem[] inventory)
    {
        System.out.println(inventory);
    }
    
    public void showHighScore(Map <String, Integer> highScore) {
        
    }
    
    public void writeToLog (String text)
    {
        log.write(text);
    }
    
}

