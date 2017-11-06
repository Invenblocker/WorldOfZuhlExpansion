package logic.processors;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import logic.Game;
import logic.GameInfo;
import logic.elements.rooms.Room;

public class TimeHolder extends TimerTask{
    
    private int DEFAULT_HELPER_COUNTDOWN;
    private int saboteurCountdown;
    private int helperCountdown;
    private double timeLeft;
    private double oxygenLeft;
    private Game game;
    private GameInfo gameInfo;
    /**
     * Creates an object of the type Timeholder
     * @param gameTime The amount of time in seconds which the game takes
     */
    public TimeHolder(int gameTime, int oxygenTime)
    {
        saboteurCountdown = 5;
        timeLeft = gameTime;
        game = Game.getInstance();
        gameInfo = game.getGameInfo();
    }

    @Override
    public void run() {
        if (!gameInfo.isGameFinished()) {    
            if (gameInfo.getDestroyedRoomsPercentage() > gameInfo.getALLOWED_ROOMS_DESTROYED_PERCENTAGE() || timeLeft <= 0) {
                gameInfo.setGameFinished(true);
                return;
            }
            
                if (saboteurCountdown == 0) {
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
        timeLeft -= (1 - gameInfo.getDestroyedRoomsPercentage()); 
        }
         
    }

    public void setSaboteurCountdown(int value) {
        this.saboteurCountdown = value;    
    }

    public void setHelperCountdown(int value) {
        this.helperCountdown = helperCountdown;
    }

    public double getOxygenLeft() {
        return oxygenLeft;
    }

    

}

