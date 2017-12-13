/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Peter og Loc
 */
public class LayeredSprite extends BaseSprite{
    
    private Map<Integer, LinkedList<BaseSprite>> sprites;
    
    /**
     * Initializes the LayeredSprite
     */
    public LayeredSprite()
    {
        sprites = new TreeMap<>();
    }
    
    /**
     * adds a sprite with a layer, to our Map.
     * @param layerKey
     * @param sprite 
     */
    public void addSprite(int layerKey, BaseSprite sprite)
    {
        LinkedList<BaseSprite> tempList = sprites.get(layerKey);
        
        if(tempList == null)
            tempList = new LinkedList<>();
        
        tempList.add(sprite);
        sprites.put(layerKey, tempList);
    }
   
    /**
     * renders our Map, so the images will be drawn in the correct order.
     * @param gc 
     */
    @Override
    public void render (GraphicsContext gc)
    {
        for (LinkedList<BaseSprite> spriteList : sprites.values()){
            for (BaseSprite sprite : spriteList){
                gc.drawImage(sprite.getImage(), sprite.getX() + getX(), sprite.getY() + getY());
            }
        }
        
    }
}
