package GUI;

import java.awt.Point;
import javafx.scene.image.Image;

/**
 *
 * @author Peter
 */
public class RoomDraw extends BaseSprite {
    private Point position;
    
    public RoomDraw(Point position, String name){
        super();
        this.position = position;
        setImage(new Image("images/room.png"));
        setPosition(position.x, position.y);
        setId(name);
        
    }
  
    
    
}