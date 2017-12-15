package GUI;

import java.awt.Point;
import javafx.scene.image.Image;

/**
 *
 * @author Peter
 */
public class SaboteurDraw extends BaseSprite
{
    private Point position;
    
    public SaboteurDraw(Point position)
    {
        super();
        this.position = position;
        setImage(new Image("images/saboteur.png"));
        setPosition(position.x, position.y);
    }
}