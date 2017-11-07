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
    private final double DEFAULT_CHANCE_OF_SABOTAGE, CHANCE_OF_SABOTAGE_GROWTH, CHANCE_OF_DOOR_SABOTAGE;
    private double chanceOfSabotage;
    private boolean chasingPlayer;
    private int stunCountdown;
    
    /**
     * @author Invenblocker
     * 
     * The constructor for the basic saboteur.
     * 
     * @param room The starting room for the saboteur.
     * @param chanceOfSabotage The starting chance that the saboteur will sabotage its current room.
     * @param chanceOfSabotageGrowth How much the chance of sabotaging the current room should grow when moving instead of sabotaging.
     */
    public Saboteur(Room room, double chanceOfSabotage, double chanceOfSabotageGrowth, double chanceOfDoorSabotage)
    {
        super(room);
        
        chasingPlayer = false;
        this.chanceOfSabotage = DEFAULT_CHANCE_OF_SABOTAGE = chanceOfSabotage;
        CHANCE_OF_SABOTAGE_GROWTH = chanceOfSabotageGrowth;
        CHANCE_OF_DOOR_SABOTAGE = chanceOfDoorSabotage;
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
        if(stunCountdown > 0) 
        {
            System.out.println("You are not supposed to call performAction while stunned");
            System.out.println("Remaining time stunned: " + stunCountdown);
            
            chasingPlayer = false;
            return stunCountdown;
        }
        
        if(chasingPlayer)
        {
            setRoom(Game.getInstance().getPlayer().getCurrentRoom());
        }
        else
        {
            if((getCurrentRoom().isOperating() || Game.getInstance().getGameInfo().getHackedExit().equals(null)) && Math.random() < chanceOfSabotage)
            {
                System.out.println("Sabotaging");
                
                if(!getCurrentRoom().isOperating())
                {
                    int sabotageExit = (int) Math.floor(Math.random() * getCurrentRoom().getCollectionOfExits().size());
                    getCurrentRoom().getCollectionOfExits().get(sabotageExit).setOpreating(false);
                    Game.getInstance().getGameInfo().setHackedExit(getCurrentRoom().getCollectionOfExits().get(sabotageExit));
                }
                else if(!Game.getInstance().getGameInfo().getHackedExit().equals(null))
                {
                    getCurrentRoom().setOperating(false);
                }
                else if(Math.random() < CHANCE_OF_DOOR_SABOTAGE)
                {
                    int sabotageExit = (int) Math.floor(Math.random() * getCurrentRoom().getCollectionOfExits().size());
                    getCurrentRoom().getCollectionOfExits().get(sabotageExit).setOpreating(false);
                    Game.getInstance().getGameInfo().setHackedExit(getCurrentRoom().getCollectionOfExits().get(sabotageExit));
                }
                else
                {
                    getCurrentRoom().setOperating(false);
                }
                
                chanceOfSabotage = DEFAULT_CHANCE_OF_SABOTAGE;
            }
            else
            {
                ArrayList<Exit> neighbors = getCurrentRoom().getCollectionOfExits();
                
                for(int i = neighbors.size() - 1; i >= 0; i--)
                {
                    if(getCurrentRoom().getExit(neighbors.get(i)).isControlRoom() || neighbors.get(i).isOperating())
                    {
                        neighbors.remove(i);
                    }
                }
                int exitIndex = (int) (Math.floor(Math.random() * neighbors.size()));
                
                setRoom(getCurrentRoom().getExit(neighbors.get(exitIndex)));
                
                chanceOfSabotage += CHANCE_OF_SABOTAGE_GROWTH;
                
                System.out.println("Saboteur moved to " + getCurrentRoom().getName());
                
                checkChasingPlayer();
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
        if(stunCountdown > 0) 
            return stunCountdown;
        if(getCurrentRoom().equals(Game.getInstance().getPlayer().getCurrentRoom()))
        {
            return(5 + (int) Math.floor(Math.random() * 6));
        }
        if(chasingPlayer)
        {
            setRoom(room);
            chanceOfSabotage += CHANCE_OF_SABOTAGE_GROWTH;
            
            if(Game.getInstance().getPlayer().getCurrentRoom().isControlRoom())
            {
                chasingPlayer = false;
            }
            
            return(5 + (int) Math.floor(Math.random() * 6));
        }
        else
        {
            return(checkChasingPlayer());
        }
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
    
    private int checkChasingPlayer()
    {
        if(stunCountdown > 0)
        {
            chasingPlayer = false;
            return stunCountdown;
        }
        if(Game.getInstance().getPlayer().getCurrentRoom().isControlRoom())
        {
            chasingPlayer = false;
            return(-1);
        }
        else
        {
            ArrayList<Exit> neighbors = getCurrentRoom().getCollectionOfExits();
            for(Exit neighbor : neighbors)
            {
                if(getCurrentRoom().getExit(neighbor).equals(Game.getInstance().getPlayer().getCurrentRoom()) && neighbor.isOperating())
                {
                    chasingPlayer = true;
                    return(5 + (int) Math.floor(Math.random() * 6));
                }
            }
            chasingPlayer = false;
            return(-1);
        }
    }
    
    public int getStunCountdown()
    {
        return(stunCountdown);
    }
    
    public void setStunCountdown(int value)
    {
        stunCountdown = value;
    }
}
