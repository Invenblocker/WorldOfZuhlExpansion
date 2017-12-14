package logic.processors;

import logic.SystemLog;
import java.util.TimerTask;
import logic.Game;
import logic.GameInfo;
import logic.elements.characters.HelperTask;
import acq.ITimeHolder;
import acq.IVisualUpdater;
import javafx.application.Platform;

public class TimeHolder extends TimerTask implements ITimeHolder
{
    // Reference to TimeHolder's own log
    private final SystemLog ACTION_LOG;
    
    // Time between each helper action except bodyduard
    private final int DEFAULT_HELPER_COUNTDOWN = 5;
    
    // The amount of time in seconds before the saboteur and helper takes
    // another action
    private int saboteurCountdown;
    private int helperCountdown;
    
    // The amount of time and oxygen left
    private double timeLeft;
    private double oxygenLeft;
    
    // The amount of time and oxygen when game started
    private double timeMax;
    private double oxygenMax;
    
    // References to other objects
    private Game game;
    private GameInfo gameInfo;
    
    // Reference to visual object which updates by the TimeHolder
    private IVisualUpdater caller;
    
    /**
     * Constructer which setup a default Timeholder and initializes values
     */
    public TimeHolder ()
    {
        ACTION_LOG = new SystemLog("TimeHolder", SystemLog.getActionLog());
        
        saboteurCountdown = 5;
        helperCountdown = 0;
        timeLeft = timeMax = 30;
        oxygenLeft = oxygenMax =  50;
        
        game = Game.getInstance();
    }
    
    /**
     * Constructer which setup a default Timeholder and initializes values
     * @param gameTime The amount of time in seconds which the game last
     * @param oxygenTime The amount of time in seconds before the player loses 
     * the game
     */
    public TimeHolder(double gameTime, double oxygenTime)
    {
        this();
        timeLeft = timeMax = gameTime;
        oxygenLeft = oxygenMax = oxygenTime;
    }
    
    /**
     * Constructer which setup a default Timeholder and initializes values
     * @param timeLeft The amount of time in seconds which the game last
     * @param oxygenLeft The amount of time in seconds before the player loses 
     * the game
     * @param timeMax
     * @param oxygenMax
     * @param saboteurCountdown The amount of time in seconds before the 
     * saboteur takes the first action
     * @param helperCountdown The amount of time in seconds before the 
     * helper takes the first action
     */
    public TimeHolder(double timeLeft, double oxygenLeft, double timeMax, double oxygenMax, int saboteurCountdown, int helperCountdown)
    {
        this();
        
        this.timeLeft = timeLeft;
        this.oxygenLeft = oxygenLeft;
        this.timeMax = timeMax;
        this.oxygenMax = oxygenMax;
        this.saboteurCountdown = saboteurCountdown;
        this.helperCountdown = helperCountdown;
    }
    
    /**
     * This method is called each time the timer ticks.
     * It checks the games state and prompts the saboteur and helper to take an 
     * action each time their countdown hits 0. It also prompts the visual 
     * object to update its visuals.
     */
    @Override
    public void run()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("Time: " + timeLeft + " Oxygen: " + oxygenLeft);
                if (!gameInfo.isGameFinished())
                {   
                    // check if game is finished
                    if (gameInfo.getDestroyedRoomsPercentage() > gameInfo.getALLOWED_ROOMS_DESTROYED_PERCENTAGE() || timeLeft <= 0 || oxygenLeft <= 0) {
                        gameInfo.setGameFinished(true);
                        caller.updateGameEnd();
                        return;
                    }

                    // check if saboteur is stunned or should move
                    if (game.getSaboteur().getStunCountdown() != 0)
                    {
                        writeToActionLog("Decrement saboteur stun countdown " + game.getSaboteur().getStunCountdown());
                        game.getSaboteur().decrementStunCountdown();
                        
                        if(game.getSaboteur().getStunCountdown() == 0)
                        {
                            checkSameRoom();
                            
                            game.getSaboteur().checkChasingPlayer();
                        }
                    }
                    else if (saboteurCountdown == 0)
                    {
                        int newCountdown = game.getSaboteur().performAction();
                        
                        checkSameRoom();
                        
                        saboteurCountdown = newCountdown;
                        
                        gameInfo.updateRoomsDestroyed();
                    }    
                    else
                    {
                        writeToActionLog("Decrement saboteur countdown " + saboteurCountdown);
                        saboteurCountdown--;
                    }

                    // check if helper should move
                    if(gameInfo.getHelper() != null)
                    {
                        HelperTask currentHelperTask = gameInfo.getHelper().getHelperTask();
                        if (helperCountdown == 0 && (currentHelperTask == HelperTask.SEARCH || currentHelperTask == HelperTask.RETURN_TO_DEFAULT))
                        {
                            int newCountdown = gameInfo.getHelper().performAction();
                            helperCountdown = newCountdown;
                        }
                    }

                    // update values for counting time
                    timeLeft -= (1 - gameInfo.getDestroyedRoomsPercentage()); 
                    oxygenLeft -= 1;

                    // update minimap if player is located in the ControlRoom
                    if ((game.getPlayer().getCurrentRoom().isControlRoom() || game.getPlayer().hasItem(game.getItems().get("pc"))))
                    {
                        caller.updateMinimap();
                        System.out.println("First update " + game.getPlayer().getCurrentRoom().isControlRoom() + " "+ game.getPlayer().hasItem(game.getItems().get("pc")));
                    }
                    else if (game.getSaboteur().isChasingPlayer())
                    {
                        caller.updateIsChasingPlayer();
                        System.out.println("Second update");
                    }
                    else if (gameInfo.isGameFinished())
                    {
                        System.out.println("game ended");
                        caller.updateGameEnd();
                    }
                    
                    caller.updateProgressBar();
                }
            }
        });
    }
    
    /**
     * Add a reference to the Visual object which visuals should be updated
     * @param _caller The visual object
     */
    public void addVisualUpdateCaller (IVisualUpdater _caller)
    {
        caller = _caller;
    }
    
    /**
     * Prompt the TimeHolder to setup references or print an error if the game
     * has not been initialized
     */
    public void setupReferences ()
    {
        if (game != null)
            gameInfo = game.getGameInfo();
        else
        {
            String msg = "TimeHolder: Could not add a reference to Game in TimeHolder";
            SystemLog.getErrorLog().writeToLog(msg);
            System.out.println(msg);
        }
    }
    
    public void setSaboteurCountdown(int value) 
    {
        this.saboteurCountdown = value;
    }
    
    public void setHelperCountdown(int value) 
    {
        this.helperCountdown = value;
    }
    
    /**
     * Returns the time until next saboteur action
     * @return The current countdown time in seconds
     */
    @Override
    public int getSaboteurCountdown () {return saboteurCountdown;}
    
    /**
     * Returns the time until next saboteur action
     * @return The current countdown time in seconds
     */
    @Override
    public int getHelperCountdown() {return helperCountdown;}
    
    /**
     * Returns the amount of time left. If 0, the game is won
     * @return The amount of time left
     */
    @Override
    public double getTimeLeft() {return timeLeft;}
    
    /**
     * Returns the amount of oxygen left. If 0, the game is lost
     * @return The amount of oxygen left
     */
    @Override
    public double getOxygenLeft() {return oxygenLeft;}

    @Override
    public double getTimeMax() {return timeMax;}

    @Override
    public double getOxygenMax() {return oxygenMax;}
    
    /**
     * Check to see if the saboteur and player is in the same room.
     * If so, the helper is killed if he is alive. Otherwise the game is lost
     */
    private void checkSameRoom()
    {
        if(game.getSaboteur().getCurrentRoom() == game.getPlayer().getCurrentRoom())
        {
            if (gameInfo.getHelper() == null)
            {
                gameInfo.setGameFinished(true);
                writeToActionLog("Game over!!");
            }
            else
            {
                gameInfo.killHepler();
                game.getSaboteur().setStunCountdown(15);
                
                game.getSaboteur().checkChasingPlayer();
                caller.updateSaboteurRoom();
            }
            
        }
    }
    
    /**
     * Used for debuging. The message is printed in the console and the log file
     * @param msg Message to log
     */
    private void writeToActionLog (String msg)
    {
        ACTION_LOG.writeToLog(msg);
        //System.out.println(msg);
    }
}

