
package org.geoint.acetate.bound;

import org.geoint.acetate.model.ComponentModel;

/**
 *
 * @param <M> model type for this component
 */
public interface BoundComponent<M extends ComponentModel> {

    /**
     * The component model for the bound data.
     * 
     * @return model
     */
    M getModel();
    
}
