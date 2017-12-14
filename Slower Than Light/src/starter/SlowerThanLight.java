/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package starter;

import GUI.GUI;
import acq.IDataFacade;
import acq.IGUI;
import acq.ILogFacade;
import database.DataFacade;
import logic.LogFacade;

/**
 *
 * @author Erik
 */
public class SlowerThanLight
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        IDataFacade data = new DataFacade();
        
        ILogFacade logik = LogFacade.getInstance();
        logik.injectData(data);
        
        IGUI gui = GUI.getInstance();
        gui.injectLogic(logik);
        
        gui.startApplication(args);
    }
    
}
