package org.geoint.acetate.model;

import java.util.Collection;

/**
 * Defines one or more {@link DataComponent components} used to define the types
 * of data with a data model.
 */
public interface DataModel {

    Collection<DataComponent> getComponents();

}
