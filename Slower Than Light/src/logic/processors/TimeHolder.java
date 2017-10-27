package logic.processors;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import logic.Game;
import logic.elements.rooms.Room;

public class TimeHolder extends TimerTask{

    private int saboteurCountdown;
    private double timeLeft;
    private Game game;
    /**
     * Creates an object of the type Timeholder
     * @param gameTime The amount of time in seconds which the game takes
     */
    public TimeHolder(int gameTime)
    {
        saboteurCountdown = 0;
        timeLeft = gameTime;
        game = Game.getInstance();
    }

    @Override
    public void run() {
        if (!game.isGameFinished()) {    
            if (game.getRoomsDestroyedPercentage() > game.getALLOWED_ROOMS_DESTROYED_PERCENTAGE() || timeLeft <= 0) {
                game.setGameFinished(true);
                return;
            }
            
                if (saboteurCountdown == 0) {
                    int newCountdown = game.getSaboteur().performAction();
                        if(game.getSaboteur().getCurrentRoom() == game.getPlayer().getCurrentRoom())
                        {
                            game.isGameFinished(true);
                            System.out.println("Game over!! ");   
                        }

                    saboteurCountdown = newCountdown;
                    
                    updateRoomsDestroyedPercentage();

                    if (game.getPlayer().getCurrentRoom().isControlRoom()) {
                        game.getGUI().updateMinimap();
                    }
                    
                }    
                else {
                    saboteurCountdown--;
                }
        timeLeft -= (1 - game.getRoomsDestroyedPercentage()); 
        }
         
    }

    public void setSaboteurCountdown(int value) {
        this.saboteurCountdown = value;    
    }

    private void updateRoomsDestroyedPercentage () {
        
        HashMap <String, Room> rooms = game.getRooms();

        int destroyedRooms = 0;
        int totalRooms = rooms.size();
          
        for (Map.Entry<String, Room> entry : rooms.entrySet()) {
            String key = entry.getKey();
            Room room = entry.getValue();
            if (!room.isOperating()) {
                destroyedRooms++;
            }
        }
        double destroyedRoomsPercentage = destroyedRooms / totalRooms;
        game.setRoomsDestroyedPercentage(destroyedRoomsPercentage);
    }
    
}
