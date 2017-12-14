package database;

import acq.IDataFacade;
import acq.ILoader;
import acq.IWriter;

/**
 *
 * @author mortenskovgaard
 */
public class DataFacade implements IDataFacade
{
    /**
     * The loader and writer which handles processing of text files
     */
    private ILoader loader;
    private IWriter writer;
       
    public DataFacade ()
    {
        this.loader = new txtLoader();
        this.writer = new txtWriter();
    }
    
    @Override
    public ILoader getLoader() {return loader;}
    
    @Override
    public IWriter getWriter() {return writer;}
}
