package GUI;

import java.awt.Point;
import javafx.scene.image.Image;

/**
 *
 * @author Peter
 */
public class PlayerDraw extends BaseSprite
{
    private Point position;
    
    public PlayerDraw(Point position)
    {
        super();
        this.position = position;
        setImage(new Image("images/player.png"));
        setPosition(position.x, position.y);
    }
}
