/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACQ;

import acq.IRoom;
/**
 *
 * @author mortenskovgaard
 */
public interface ISaboteurInfo 
{
  
    IRoom currentRoom();
    
    double intChanceOfSabotage();
    
    double chanceOfSabotageGrowth();
    
    double chanceOfDoorSabotage();
    
    double currentChanceOfSabotage();

    int stunCountdown();    
}
