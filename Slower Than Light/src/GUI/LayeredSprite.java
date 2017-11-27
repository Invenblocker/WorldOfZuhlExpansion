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
    
    
    public LayeredSprite()
    {
        sprites = new TreeMap<>();
    }
    
    public void addSprite(int layerKey, BaseSprite sprite)
    {
        LinkedList<BaseSprite> tempList = sprites.get(layerKey);
        
        if(tempList == null)
            tempList = new LinkedList<>();
        
        tempList.add(sprite);
        sprites.put(layerKey, tempList);
    }
    
    public void addSprite(int layerKey, BaseSprite sprite, boolean samePositionAsParent)
    {
        sprite.setPosition(getX(), getY());
        addSprite(layerKey, sprite);
    }
    
    @Override
    public void render (GraphicsContext gc)
    {
        for (LinkedList<BaseSprite> spriteList : sprites.values()){
            for (BaseSprite sprite : spriteList){
                System.out.println("Printer: " + sprite.getPosition());
                gc.drawImage(sprite.getImage(), sprite.getX() + getX(), sprite.getY() + getY());
                if(sprite.getId() != null){
                    gc.fillText(sprite.getId(), sprite.getX() + 25,sprite.getY() + 50 );
                }
            }
        }
        
    }
}