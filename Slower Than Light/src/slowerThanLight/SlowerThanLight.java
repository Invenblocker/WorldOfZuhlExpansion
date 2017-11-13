/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowerThanLight;

import database.SystemLog;
import database.txtLoader;
import java.io.FileNotFoundException;
import logic.Game;

/**
 *
 * @author Erik
 */
public class SlowerThanLight {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        Game game = Game.getInstance();
        txtLoader loader = new txtLoader("assets/maps/bigRectangle.txt");
        loader.newGame();
        
        game.addGameLoader(loader);
        game.play();
    }
    
}
