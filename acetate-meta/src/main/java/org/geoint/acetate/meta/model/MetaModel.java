
package org.geoint.acetate.meta.model;

import java.util.Collection;
import org.geoint.acetate.meta.MetaVersion;

/**
 * Defines a metamodel which make use of the object models.
 */
public interface MetaModel {

    /**
     * Unique name of the meta model.
     * 
     * @return meta model name
     */
    String getName();
    
    /**
     * Version of the meta model.
     * 
     * @return meta model version
     */
    MetaVersion getVersion();
    
    /**
     * Model components of interest to the meta model.
     * 
     * @return model components of the meta model
     */
    Collection<ObjectModel> getModels();
}
