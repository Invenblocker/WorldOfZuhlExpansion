/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.elements.characters;

import logic.elements.rooms.*;
/**
 *
 * @author Johnn
 */
public class Saboteur extends RoomHopper
{
    private double chanceOfSabotage;
    private final double DEFAULT_CHANCE_OF_SABOTAGE, CHANCE_OF_SABOTAGE_GROWTH;
    private boolean chasingPlayer;
    
    Saboteur(Room room, double chanceOfSabotage, double chanceOfSabotageGrowth)
    {
        super(room);
        
        chasingPlayer = false;
        this.chanceOfSabotage = DEFAULT_CHANCE_OF_SABOTAGE = chanceOfSabotage;
        CHANCE_OF_SABOTAGE_GROWTH = chanceOfSabotageGrowth;
    }
    
    
}
