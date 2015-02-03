package org.geoint.acetate.metamodel;

import java.util.Collection;

/**
 * Hierarchical data field node which models its own structure as well as
 * provides access to its child fields (if present).
 *
 * @param <P> (parent) data item type that contains this field
 * @param <F> the data type of the source java object
 */
public interface FieldModel<P, F> {

    /**
     * Absolute names the field is known as in this model.
     *
     * @return names this field is known as in this model
     */
    Collection<String> getNames();

//    /**
//     * Accessor to retrieve the field value from the base model object.
//     *
//     * @return accessor to retrieve the
//     */
//    FieldAccessor<P, F> getAccessor();
//
//    /**
//     * The mutator/setter for the field.
//     *
//     * @return field setter
//     */
//    FieldSetter<P, F> getSetter();
    
    /**
     * Retrieve the value of the field.
     * 
     * @param container object containing the field
     * @return the value of the field
     */
    F getValue(P container);
    
    /**
     * Set the value of the field.
     * 
     * @param container object containing the field
     * @param value value of the field
     */
    void setValue(P container, F value);

}
