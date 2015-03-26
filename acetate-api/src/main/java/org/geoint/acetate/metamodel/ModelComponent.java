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

    /**
     * Retrieve a child data property (field or aggregate) of this component by
     * its name or alias.
     *
     * @param alias field name or alias
     * @return matching component or null if no child component matches
     */
    ModelComponent<T> getComponent(String alias);

    /**
     * Retrieve a child aggregate of this component by its name or alias.
     *
     * Similar to {@link #getComponent(String) } but ignores fields.
     *
     * @param alias aggregate name or alias
     * @return matching aggregate model or null if not child aggregate matches
     */
    ModelClass<T> getAggregate(String alias);

}
