/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    public ILoader loader;
    public IWriter writer;
       
    public DataFacade ()
    {
        this.loader = new txtLoader();
        this.writer = new txtWriter();
    }
    
    @Override
    public ILoader getLoader()
    {
        return loader;
        
    }
    
    @Override
    public IWriter getWriter()
    {
        return writer;
        
    }
}
