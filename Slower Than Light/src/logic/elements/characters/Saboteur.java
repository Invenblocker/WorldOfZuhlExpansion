/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

import logic.*;
import logic.elements.*;
import logic.elements.rooms.*;
import java.util.*;
/**
 *
 * @author Johnn
 */
public class Saboteur extends RoomHopper
{
    private double chanceOfSabotage;
    private final double DEFAULT_CHANCE_OF_SABOTAGE, CHANCE_OF_SABOTAGE_GROWTH;
    private boolean chasingPlayer;
    
    Saboteur(Room room, double chanceOfSabotage, double chanceOfSabotageGrowth)
    {
        super(room);
        
        chasingPlayer = false;
        this.chanceOfSabotage = DEFAULT_CHANCE_OF_SABOTAGE = chanceOfSabotage;
        CHANCE_OF_SABOTAGE_GROWTH = chanceOfSabotageGrowth;
    }
    
    int performAction()
    {
        if(chasingPlayer)
        {
            
        }
        
        return(5 + (int) Math.floor(Math.random() * 6));
    }
    
    int chasePlayer(Room room)
    {
        if(chasingPlayer)
        {
            setRoom(room);
            return(5 + (int) Math.floor(Math.random() * 6));
        }
        else
        {
            return(-1);
        }
    }
    
    void setChasingPlayer(boolean value)
    {
        chasingPlayer = value;
    }
    
    boolean isChasingPlayer()
    {
        return(chasingPlayer);
    }
}