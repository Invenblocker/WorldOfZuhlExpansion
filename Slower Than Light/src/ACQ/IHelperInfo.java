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
public interface IHelperInfo 
{

    IRoom currentIRoom();
    
    String name();
    
    double intChanceOfDiscovery();
    
    double chanceOfDiscoveryGrowth();
    
    double currentChanceOfDiscovery();
    
    String helperTaskSting();
    
}