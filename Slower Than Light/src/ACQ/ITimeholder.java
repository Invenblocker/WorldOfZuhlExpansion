/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acq;

/**
 *
 * @author Johnn
 */
public interface ITimeholder {

    int getHelperCountdown();

    double getOxygenLeft();

    int getSaboteurCountdown();

    double getTimeLeft();

    void run();

    void setHelperCountdown(int value);

    void setSaboteurCountdown(int value);

    void setupReferences();
    
}
