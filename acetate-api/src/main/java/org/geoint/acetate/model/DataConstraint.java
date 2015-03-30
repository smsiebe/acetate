
package org.geoint.acetate.model;

/**
 *
 * @param <T> data type for which this constraint applies
 */
public interface DataConstraint<T> {

    void validate (T data);
    
}
