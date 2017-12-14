package acq;

/**
 *
 * @author mortenskovgaard
 */
public interface IDataFacade
{
    
    ILoader getLoader();
    
    IWriter getWriter();
    
}
