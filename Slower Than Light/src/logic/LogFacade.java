/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import ACQ.IDataFacade;
import acq.ILogFacade;
import acq.IHelper;
import acq.IPlayer;
import acq.ISaboteur;
import acq.ITimeholder;
import database.DataFacade;
import logic.elements.characters.Helper;
import logic.Game.*;
import logic.elements.characters.Saboteur;
import logic.processors.TimeHolder;

/**
 *
 * @author Johnn
 */
public class LogFacade implements ILogFacade 
{
    private static ILogFacade instance;
    
    
    public static ILogFacade getInstance()
    {
        if (instance == null)
        {
            instance = new LogFacade();
        }
        return instance;
    }
    
    private IDataFacade data;
    
    public LogFacade()
    {
        
    }   
    @Override
    public void injectData (IDataFacade dataFacade)
    {
        dataFacade = data;
    }
    
    @Override
    public void processCommand (String Command)
    {
        
    }
    
    @Override
    public ITimeholder getTimeholder()
    {
        return getTimeholder();
    } 
    
    @Override
    public IPlayer getPlayer()
    {
       return getPlayer();
    }
    
    @Override
    public ISaboteur getSaboteur()
    {
       return getSaboteur();
    }
    
    @Override
    public IHelper getHelper()
    {
        return getHelper();
    }

    

}
