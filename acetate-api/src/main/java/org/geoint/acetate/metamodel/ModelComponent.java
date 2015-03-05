package org.geoint.acetate.metamodel;

import java.util.Set;

/**
 * A component of a data model.
 * 
 * The ModelComponent is the abstract parent type of a Composite Pattern
 * that defines the data model.
 * 
 * @see ModelClass
 * @see ModelField
 * @param <T> value type
 */
public interface ModelComponent<T> {

    /**
     * Data component name, normally the actual name of the java class field.
     *
     * @return name of the field
     */
    String getName();

    /**
     * Alternative names (aliases) that can be used to address this field.
     *
     * @return alternative names used by this field
     */
    Set<String> getAliases();

}
