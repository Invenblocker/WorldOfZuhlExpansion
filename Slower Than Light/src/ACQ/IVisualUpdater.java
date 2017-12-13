/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acq;

/**
 *
 * @author Erik
 */
public interface IVisualUpdater
{
    void updateMinimap();
    
    void updateSaboteurRoom();
    
    void updateIsChasingPlayer();
    
    boolean updateGameEnd();
}
