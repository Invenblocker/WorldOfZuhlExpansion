package logic.processors;

import logic.SystemLog;
import java.util.TimerTask;
import logic.Game;
import logic.GameInfo;
import logic.elements.characters.HelperTask;
import logic.elements.rooms.Room;

public class TimeHolder extends TimerTask{
    
    private final int DEFAULT_HELPER_COUNTDOWN = 5;
    private int saboteurCountdown;
    private int helperCountdown;
    private double timeLeft;
    private double oxygenLeft;
    private Game game;
    private GameInfo gameInfo;
    
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

    @Override
    public void run()
    {
        if (!gameInfo.isGameFinished()) {    
            if (gameInfo.getDestroyedRoomsPercentage() > gameInfo.getALLOWED_ROOMS_DESTROYED_PERCENTAGE() || timeLeft <= 0 || oxygenLeft <= 0) {
                gameInfo.setGameFinished(true);
                return;
            }
                if (game.getSaboteur().getStunCountdown() != 0)
                {
                    game.getSaboteur().decrementStunCountdown();
                    System.out.println("Decrement stun countdown");
                }
                else if (saboteurCountdown == 0)
                {
                    int newCountdown = game.getSaboteur().performAction();
                        if(game.getSaboteur().getCurrentRoom() == game.getPlayer().getCurrentRoom())
                        {
                            gameInfo.setGameFinished(true);
                            System.out.println("Game over!! ");   
                        }

                    saboteurCountdown = newCountdown;
                    
                    gameInfo.updateRoomsDestroyed();

                    if (game.getPlayer().getCurrentRoom().isControlRoom()) {
                        Room saboteurRoom = game.getSaboteur().getCurrentRoom();
                        Room[] destroyedRooms = gameInfo.getDestroyedRooms();
                        game.getGUI().updateMinimap(saboteurRoom, destroyedRooms);
                    }
                    
                }    
                else {
                    saboteurCountdown--;
                }
                if (helperCountdown == 0 && (gameInfo.getHelper().getHelperTask() == HelperTask.SEARCH || gameInfo.getHelper().getHelperTask() == HelperTask.RETURN_TO_DEFAULT))
                {
                    int newCountdown = gameInfo.getHelper().performAction();
                        helperCountdown = newCountdown;
                }
                
            timeLeft -= (1 - gameInfo.getDestroyedRoomsPercentage()); 
            oxygenLeft -= 1;
        }
         
    }
    
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

    public void setSaboteurCountdown(int value) 
    {
        this.saboteurCountdown = value;    
    }
    public void setHelperCountdown(int value) 
    {
        this.helperCountdown = value;
    }
    
    public int getSaboteurCountdown () {return saboteurCountdown;}
    
    public int getHelperCountdown()
    {
        return helperCountdown;
    }
    public double getOxygenLeft()
    {
        return oxygenLeft;
    }
    public double getTimeLeft()
    {
        return timeLeft;
    }
}

