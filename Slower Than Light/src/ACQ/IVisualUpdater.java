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
    
    void updateProgressBar();
    
    boolean updateGameEnd();
}
