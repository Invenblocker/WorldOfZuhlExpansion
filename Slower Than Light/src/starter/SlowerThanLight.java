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
import logic.Game;
import logic.LogFacade;

/**
 *
 * @author Erik
 */
public class SlowerThanLight {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        /*Game game = Game.getInstance();
        txtLoader loader = new txtLoader();
        loader.newGame("assets/maps/bigRectangle.txt");
        
        game.setupGame(loader);
        game.play();*/
        
        IDataFacade data = new DataFacade();
        data.getLoader().loadGame("assets/maps/bigRectangle.txt");
        
        ILogFacade logik = LogFacade.getInstance();
        logik.injectData(data);
        System.out.println(logik);
        IGUI gui = GUI.getInstance();
        gui.injectLogic(logik);
        
        Game.getInstance().play();
    }
    
}
