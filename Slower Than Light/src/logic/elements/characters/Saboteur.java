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
 * @author Invenblocker & JN97
 */
public class Saboteur extends RoomHopper
{
    private double chanceOfSabotage;
    private final double DEFAULT_CHANCE_OF_SABOTAGE, CHANCE_OF_SABOTAGE_GROWTH;
    private boolean chasingPlayer;
    
    /**
     * @author Invenblocker
     * 
     * The constructor for the basic saboteur.
     * 
     * @param room The starting room for the saboteur.
     * @param chanceOfSabotage The starting chance that the saboteur will sabotage its current room.
     * @param chanceOfSabotageGrowth How much the chance of sabotaging the current room should grow when moving instead of sabotaging.
     */
    public Saboteur(Room room, double chanceOfSabotage, double chanceOfSabotageGrowth)
    {
        super(room);
        
        chasingPlayer = false;
        this.chanceOfSabotage = DEFAULT_CHANCE_OF_SABOTAGE = chanceOfSabotage;
        CHANCE_OF_SABOTAGE_GROWTH = chanceOfSabotageGrowth;
    }
    
    /**
     * @author Invenblocker
     * 
     * A simple AI that causes the saboteur to act.
     * 
     * @return an integer dictating the amount of time to the next action.
     */
    public int performAction()
    {
        if(chasingPlayer)
        {
            setRoom(Game.getInstance().getPlayer().getCurrentRoom());
        }
        else
        {
            if(getCurrentRoom().isOperating() && Math.random() < chanceOfSabotage)
            {
                getCurrentRoom().setOperating(false);
                chanceOfSabotage = DEFAULT_CHANCE_OF_SABOTAGE;
            }
            else
            {
                ArrayList<String> neighbors = getCurrentRoom().getCollectionOfExits();
                
                for(int i = neighbors.size() - 1; i >= 0; i--)
                {
                    if(getCurrentRoom().getExit(neighbors.get(i)).isControlRoom())
                    {
                        neighbors.remove(i);
                    }
                }
                
                setRoom(getCurrentRoom().getExit(neighbors.get((int) Math.floor(Math.random() * neighbors.size()))));
                
                chanceOfSabotage += CHANCE_OF_SABOTAGE_GROWTH;
                
                neighbors = getCurrentRoom().getCollectionOfExits();
                
                for(int i = 0; i < neighbors.size(); i++)
                {
                    if(getCurrentRoom().getExit(neighbors.get(i)).equals(Game.getInstance().getPlayer().getCurrentRoom()))
                    {
                        chasingPlayer = true;
                    }
                }
            }
        }
        
        return(5 + (int) Math.floor(Math.random() * 6));
    }
    
    /**
     * @author Invenblocker
     * 
     * Causes the saboteur to chase the player by entering the room entered in
     * the parameter if they're actively chasing. Returns -1 if not actively
     * chasing, if actively chasing, returns an integer from 5 to 10 both
     * both inclusive stating the amount of time before the saboteur's next
     * action.
     * 
     * @param room The room in which the saboteur should chase
     * @return The amount of time to the next action (-1 if not actively chasing)
     */
    public int chasePlayer(Room room)
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
    
    /**
     * @author Invenblocker
     * 
     * Modifies whether the saboteur is chasing the player.
     * 
     * @param value true if the saboteur should chase, false if not.
     */
    public void setChasingPlayer(boolean value)
    {
        chasingPlayer = value;
    }
    
    /**
     * @author Invenblocker
     * 
     * Checks to see if the saboteur is chasing the player.
     * 
     * @return true if chasing, false if not.
     */
    public boolean isChasingPlayer()
    {
        return(chasingPlayer);
    }
}
