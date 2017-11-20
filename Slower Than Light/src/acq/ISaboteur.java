/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acq;

import acq.IRoomHopper;

/**
 *
 * @author mortenskovgaard
 */
public interface ISaboteur extends IRoomHopper {

    /**
     * @author Invenblocker
     *
     * Checks to see if the saboteur is chasing the player.
     *
     * @return true if chasing, false if not.
     */
    boolean isChasingPlayer();
    
}
