package GUI;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Peter
 */
public class BaseSprite extends ImageView{
    
     public BaseSprite(){
        super();
    }
     
    public BaseSprite(Image image){
        super(image);
    }
    
    public BaseSprite(Image image, double x, double y, double width, double height){
        this(image);
        setX(x);
        setY(y);
        setFitWidth(width);
        setFitHeight(height);
    }
    
    public void render(GraphicsContext gc){
        gc.drawImage(this.getImage(), xProperty().doubleValue(), yProperty().doubleValue());
    }
     
    public void setSize(double width, double height) {
        setFitWidth(width);
        setFitHeight(height);
    }
    
    public void setPosition(double x, double y){
        setX(x);
        setY(y);
    }
    
    public String getPosition(){
        return(this.getX() + " , " + this.getY());
    }
}
