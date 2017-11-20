/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACQ;

/**
 *
 * @author mortenskovgaard
 */
public interface ITimeHolderInfo 
{

    double gameTime();
    
    double oxygenTime();
    
    int helperCountdown();
    
    int saboteurCountdown();
    
}
