package acq;

/**
 *
 * @author Erik
 */
public interface IGUI
{
    void startApplication(String[] args);
    
    void injectLogic (ILogFacade logFacade);
}
