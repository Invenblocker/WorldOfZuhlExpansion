/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACQ;

import acq.ILoader;
import acq.IWriter;

/**
 *
 * @author mortenskovgaard
 */
public interface IDataFacade {

    ILoader getLoader();

    IWriter getWriter();
    
}
