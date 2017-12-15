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
        
        ILogFacade logic = LogFacade.getInstance();
        logic.injectData(data);
        
        IGUI gui = GUI.getInstance();
        gui.injectLogic(logic);
        
        gui.startApplication(args);
    }
    
}
