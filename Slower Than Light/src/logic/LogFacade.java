/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import acq.IHelper;

/**
 *
 * @author Johnn
 */
public class LogFacade implements ILogFacade 
{
    private static ILogFacade instance; 
    
    public static ILogFacade getInstance()
    {
        
    }
    public LogFacade()
    {
        
    }   
    @Override
    public void injectData (IDataFacade dataFacade)
    {
        
    }
    @Override
    public void processCommand (String Command)
    {
        
    }
    @Override
    public ITimeholder getTimeholder()
    {
        
    }   
    @Override
    public IPlayer getPlayer()
    {
        
    }
    @Override
    public ISaboteur getSaboteur()
    {
        
    }
    @Override
    public IHelper getHelper()
    {
        
    }
    

}
