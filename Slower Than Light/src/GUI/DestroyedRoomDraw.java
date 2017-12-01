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
public class DestroyedRoomDraw extends BaseSprite
{
    private Point position;
    
    public DestroyedRoomDraw(Point position, String name)
    {
        super();
        this.position = position;
        setImage(new Image("images/destroyedRoom.png"));
        setPosition(position.x, position.y);
        setId(name);
        
    }
  
    
    
}