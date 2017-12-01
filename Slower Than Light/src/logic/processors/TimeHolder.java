package logic.processors;

import logic.SystemLog;
import java.util.TimerTask;
import logic.Game;
import logic.GameInfo;
import logic.elements.characters.HelperTask;
import logic.elements.rooms.Room;
import acq.ITimeHolder;
import acq.IVisualUpdater;

public class TimeHolder extends TimerTask implements ITimeHolder{
    
    private final int DEFAULT_HELPER_COUNTDOWN = 5;
    private int saboteurCountdown;
    private int helperCountdown;
    private double timeLeft;
    private double oxygenLeft;
    private Game game;
    private GameInfo gameInfo;
    
    private IVisualUpdater caller;
    
    public TimeHolder ()
    {
        saboteurCountdown = 5;
        helperCountdown = 0;
        timeLeft = 30;
        oxygenLeft = 50;
        game = Game.getInstance();
    }
    
    /**
     * Creates an object of the type Timeholder
     * @param gameTime The amount of time in seconds which the game takes
     * @param oxygenTime The amount of time in seconds before the player loses the game
     */
    public TimeHolder(double gameTime, double oxygenTime)
    {
        this();
        timeLeft = gameTime;
        oxygenLeft = oxygenTime;
    }
    
    public void addVisualUpdateCaller (IVisualUpdater _caller)
    {
        caller = _caller;
    }

    @Override
    public void run()
    {
        if (!gameInfo.isGameFinished())
        {   
            // check if game is finished
            if (gameInfo.getDestroyedRoomsPercentage() > gameInfo.getALLOWED_ROOMS_DESTROYED_PERCENTAGE() || timeLeft <= 0 || oxygenLeft <= 0) {
                gameInfo.setGameFinished(true);
                return;
            }
            
            // check if saboteur is stunned or should move
            if (game.getSaboteur().getStunCountdown() != 0)
            {
                game.getSaboteur().decrementStunCountdown();
                SystemLog.getActionLog().writeToLog("Decrement stun countdown");
                System.out.println("Decrement stun countdown");
            }
            else if (saboteurCountdown == 0)
            {
                int newCountdown = game.getSaboteur().performAction();
                
                if(game.getSaboteur().getCurrentRoom() == game.getPlayer().getCurrentRoom())
                {
                    gameInfo.setGameFinished(true);
                    SystemLog.getActionLog().writeToLog("Game over!!");
                    System.out.println("Game over!! ");   
                }

                saboteurCountdown = newCountdown;

                gameInfo.updateRoomsDestroyed();
            }    
            else
            {
                saboteurCountdown--;
            }
            
            // check if helper should move
            HelperTask currentHelperTask = gameInfo.getHelper().getHelperTask();
            if (helperCountdown == 0 && (currentHelperTask == HelperTask.SEARCH || currentHelperTask == HelperTask.RETURN_TO_DEFAULT))
            {
                int newCountdown = gameInfo.getHelper().performAction();
                helperCountdown = newCountdown;
            }
            
            // update values for counting time
            timeLeft -= (1 - gameInfo.getDestroyedRoomsPercentage()); 
            oxygenLeft -= 1;
            
            // update minimap if player is located in the ControlRoom
            if (game.getPlayer().getCurrentRoom().isControlRoom())
            {
                caller.updateWithTimer();
            } 
        }
    }
    
    @Override
    public void setupReferences ()
    {
        if (game != null)
            gameInfo = game.getGameInfo();
        else
        {
            String msg = "Could not add a reference to Game in TimeHolder";
            SystemLog.getErrorLog().writeToLog(msg);
            System.out.println(msg);
        }
    }

    @Override
    public void setSaboteurCountdown(int value) 
    {
        this.saboteurCountdown = value;    
    }
    @Override
    public void setHelperCountdown(int value) 
    {
        this.helperCountdown = value;
    }
    
    @Override
    public int getSaboteurCountdown () {return saboteurCountdown;}
    
    @Override
    public int getHelperCountdown()
    {
        return helperCountdown;
    }
    @Override
    public double getOxygenLeft()
    {
        return oxygenLeft;
    }
    @Override
    public double getTimeLeft()
    {
        return timeLeft;
    }
}

