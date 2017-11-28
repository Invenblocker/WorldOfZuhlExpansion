/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Point;
import javafx.scene.image.Image;

/**
 *
 * @author Peter
 */
public class SaboteurDraw extends BaseSprite{
    private Point position;
    
    public SaboteurDraw(Point position){
        super();
        this.position = position;
        setImage(new Image("assets/saboteur.png"));
        setPosition(position.x, position.y);
        
    }
}