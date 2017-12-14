/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

import acq.ISaboteur;
import logic.*;
import logic.elements.*;
import logic.elements.rooms.*;
import java.util.*;
import logic.SystemLog;
/**
 * The main and only antagonist in the game.
 * @author Invenblocker & JN97
 */
public class Saboteur extends RoomHopper implements ISaboteur
{
    public final double DEFAULT_CHANCE_OF_SABOTAGE, CHANCE_OF_SABOTAGE_GROWTH, CHANCE_OF_DOOR_SABOTAGE;
    private double chanceOfSabotage;
    private boolean chasingPlayer;
    private int stunCountdown;
    private final SystemLog ACTION_LOG, ERROR_LOG;
    
    /**
     * The constructor for the basic saboteur.
     * @author Invenblocker
     * @param room The starting room for the saboteur.
     * @param chanceOfSabotage The starting chance that the saboteur will sabotage its current room.
     * @param chanceOfSabotageGrowth How much the chance of sabotaging the current room should grow when moving instead of sabotaging.
     * @param chanceOfDoorSabotage
     */
    public Saboteur(Room room, double chanceOfSabotage, double chanceOfSabotageGrowth, double chanceOfDoorSabotage)
    {
        super(room);
        
        chasingPlayer = false;
        this.chanceOfSabotage = DEFAULT_CHANCE_OF_SABOTAGE = chanceOfSabotage;
        CHANCE_OF_SABOTAGE_GROWTH = chanceOfSabotageGrowth;
        CHANCE_OF_DOOR_SABOTAGE = chanceOfDoorSabotage;
        ACTION_LOG = new SystemLog("Saboteur", SystemLog.getActionLog());
        ERROR_LOG = new SystemLog("Saboteur", SystemLog.getErrorLog());
    }
    
    /**
     * A simple AI that causes the saboteur to act.
     * @author Invenblocker
     * @return an integer dictating the amount of time to the next action.
     */
    public int performAction()
    {
        if(stunCountdown > 0) 
        {
            ERROR_LOG.writeToLog("The saboteur is not supposed to call performAction() while stuunned. Remaining time stunned: " + stunCountdown + '.');
            
            chasingPlayer = false;
            return stunCountdown;
        }
        
        if(chasingPlayer)
        {
            setRoom(Game.getInstance().getPlayer().getCurrentRoom());
        }
        else
        {
            if(((getCurrentRoom().isOperating() && getCurrentRoom() instanceof ItemRoom) || Game.getInstance().getGameInfo().getHackedExit() == null) && Math.random() < chanceOfSabotage)
            {
                if(!getCurrentRoom().isOperating() || !(getCurrentRoom() instanceof ItemRoom))
                {
                    int sabotageExit = (int) Math.floor(Math.random() * getCurrentRoom().getCollectionOfExits().size());
                    getCurrentRoom().getCollectionOfExits().get(sabotageExit).setOperating(false);
                    Game.getInstance().getGameInfo().setHackedExit(getCurrentRoom().getCollectionOfExits().get(sabotageExit));
                    ACTION_LOG.writeToLog("Sabotaged the exit between \"the " + getCurrentRoom().getName() + "\" and \"the " + getCurrentRoom().getExit(getCurrentRoom().getCollectionOfExits().get(sabotageExit)).getName() + "\".");
                }
                else if(Game.getInstance().getGameInfo().getHackedExit() != null)
                {
                    getCurrentRoom().setOperating(false);
                    ACTION_LOG.writeToLog("Sabotaged the room: \"" + getCurrentRoom().getName() + "\".");
                }
                else if(Math.random() < CHANCE_OF_DOOR_SABOTAGE)
                {
                    int sabotageExit = (int) Math.floor(Math.random() * getCurrentRoom().getCollectionOfExits().size());
                    getCurrentRoom().getCollectionOfExits().get(sabotageExit).setOperating(false);
                    Game.getInstance().getGameInfo().setHackedExit(getCurrentRoom().getCollectionOfExits().get(sabotageExit));
                    ACTION_LOG.writeToLog("Sabotaged the exit between \"the " + getCurrentRoom().getName() + "\" and \"the " + getCurrentRoom().getExit(getCurrentRoom().getCollectionOfExits().get(sabotageExit)).getName() + "\".");
                }
                else
                {
                    getCurrentRoom().setOperating(false);
                    ACTION_LOG.writeToLog("Sabotaged the room: \"" + getCurrentRoom().getName() + "\".");
                }
                
                chanceOfSabotage = DEFAULT_CHANCE_OF_SABOTAGE;
            }
            else
            {
                ArrayList<Exit> neighbors = getCurrentRoom().getCollectionOfExits();
                
                for(int i = neighbors.size() - 1; i >= 0; i--)
                    if(getCurrentRoom().getExit(neighbors.get(i)).isControlRoom() || !neighbors.get(i).isOperating())
                        neighbors.remove(i);
                
                if (neighbors.isEmpty())
                {
                    ACTION_LOG.writeToLog("Saboteur tried to move, but all exits where removed. Current room: \"" + getCurrentRoom().getName() + "\".");
                    return -1;
                }
                
                int exitIndex = (int) (Math.floor(Math.random() * neighbors.size()));
                
                setRoom(getCurrentRoom().getExit(neighbors.get(exitIndex)));
                
                chanceOfSabotage += CHANCE_OF_SABOTAGE_GROWTH;
                
                //ACTION_LOG.writeToLog("Moved to \"the " + getCurrentRoom().getName() + "\".");
                
                checkChasingPlayer();
            }
        }
        
        return(5 + (int) Math.floor(Math.random() * 6));
    }
    
    /**
     * Causes the saboteur to chase the player by entering the room entered in
     * the parameter if they're actively chasing. Returns -1 if not actively
     * chasing, if actively chasing, returns an integer from 5 to 10 both
     * both inclusive stating the amount of time before the saboteur's next
     * action.
     * @author Invenblocker
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
     * Checks to see if the saboteur is chasing the player.
     * @author Invenblocker
     * @return true if chasing, false if not.
     */
    @Override
    public boolean isChasingPlayer()
    {
        return(chasingPlayer);
    }
    
    /**
     * Checks to see if the Saboteur should chase the player
     * @author Invenblocker
     * @return The amount of time that shoud pass before the next action if the
     *         Saboteur starts chasing. -1 if it doesn't.
     */
    public int checkChasingPlayer()
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
    
    /**
     * @author Invenblocker
     * @return The current stun countdown
     */
    public int getStunCountdown()
    {
        return(stunCountdown);
    }
    
    /**
     * @author JN97
     * @param value The value the stun countdown should be set to.
     */
    public void setStunCountdown(int value)
    {
        stunCountdown = value;
    }
    
    /**
     * Adds or subtracts time to or from the stun coundown.
     * 
     * @author JN97
     * @param value The value to be added, use negative value for subtraction.
     */
    public void addStunCountdown(int value)
    {
        stunCountdown += value;
    }
    
    /**
     * Decrements the stun countdown by 1
     * 
     * @author Invenblocker
     */
    public void decrementStunCountdown()
    {
        addStunCountdown(-1);
    }
    
    /**
     * @author Invenblocker
     * @return The chance of sabotage
     */
    public double getChanceOfSabotage()
    {
        return(chanceOfSabotage);
    }
    
    /**
     * @author JN97
     * @param value The value the chance of sabotage should be set to 
     */
    public void setChanceOfSabotage(double value)
    {
        chanceOfSabotage = value;
    }
    
    /**
     * @author Invenblocker
     * @return The Saboteur's ACTION_LOG
     */
    @Override
    public SystemLog getActionLog()
    {
        return(ACTION_LOG);
    }
    
    /**
     * @author Invenblocker
     * @return The Saboteur's ERROR_LOG
     */
    @Override
    public SystemLog getErrorLog()
    {
        return(ERROR_LOG);
    }
}
