package logic.processors;

import java.util.TimerTask;

public class TimeHolder extends TimerTask{

    private int saboteurCountdown;
    private double timeLeft;

    /**
     * Creates an object of the type Timeholder
     * @param gameTime The amount of time in seconds which the game takes
     */
    public TimeHolder(int gameTime)
    {
        saboteurCountdown = 0;
        timeLeft = gameTime;
    }

    @Override
    public void run()
    {
        
    }

    public void setSaboteurCountdown(int countDown)
    {
        
    }

    
}
