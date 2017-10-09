/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

/**
 *
 * @author barth_000
 */
public class Item {
    private String name;
    
    Item(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return(name);
    }
}
