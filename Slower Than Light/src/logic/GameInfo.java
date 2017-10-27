/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author Erik
 */
public class GameInfo {
    
    private final double ALLOWED_ROOMS_DESTROYED_PERCENTAGE = 0.7;
    private double roomsDestroyedPercentage;
    
    private boolean gameFinished;
    
    public GameInfo()
    {
        roomsDestroyedPercentage = 0;
        gameFinished = false;
    }
    
    public double getALLOWED_ROOMS_DESTROYED_PERCENTAGE() {return ALLOWED_ROOMS_DESTROYED_PERCENTAGE;}

    public double getRoomsDestroyedPercentage() {return roomsDestroyedPercentage;}
    public void setRoomsDestroyedPercentage(double roomsDestroyedPercentage) {
        this.roomsDestroyedPercentage = roomsDestroyedPercentage;
    }
    
    public boolean isGameFinished () {return gameFinished;}
    public void setGameFinished(boolean value)
    {
        gameFinished = value;
    }
}
