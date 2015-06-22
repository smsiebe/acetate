
package org.geoint.acetate.meta.model;

import java.util.Set;

/**
 * Model of the return type of an Operation.
 */
public interface ReturnModel<R> {

    ObjectModel<R> getModel();
    
    Set<ThrowableModel<?>> getExceptions();
}
