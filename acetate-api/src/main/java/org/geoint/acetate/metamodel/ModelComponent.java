package org.geoint.acetate.metamodel;

import java.util.Set;

/**
 * A component of a data model.
 *
 * The ModelComponent is the abstract parent type of a Composite Pattern that
 * defines the data model.
 *
 * @see ModelClass
 * @see ModelField
 * @param <T> value type
 */
public interface ModelComponent<T> {

    /**
     * Return the model component name.
     *
     * @return model component name
     */
    String getName();

    /**
     * All the names this component may be called in a template, as defined by
     * the model.
     *
     * @return alternative names used by this field
     */
    Set<String> getAliases();

}
