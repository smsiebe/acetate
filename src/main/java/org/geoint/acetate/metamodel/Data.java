package org.geoint.acetate.metamodel;

/**
 * Metadata model of a data structure.
 */
public class Data<T> {

    private final Class<T> class;
    private final DataContext<C, T> context;
    private Hierarchy<DataElement<?>> elements;
    
    
}
