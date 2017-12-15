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
