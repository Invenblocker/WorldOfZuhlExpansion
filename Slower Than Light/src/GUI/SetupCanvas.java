/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Point;
import java.util.HashMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Peter
 */
public class SetupCanvas extends Canvas {
    
    GraphicsContext gc;
    
    
    
    
     public SetupCanvas(double width, double height)
    {
        super(width, height);
        intialize();
    }

    private void intialize() {
        gc = getGraphicsContext2D();
       // DestroyedRoom ds = new DestroyedRoom(new Point(0,0));
      //  ds.setPosition(0, 0);
       // ds.render(gc);
    }
    
    
    

    
    
}
