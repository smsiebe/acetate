package org.geoint.acetate.metamodel;

/**
 * Model which contain one or more child model components.
 *
 * @param <T> object type this model represents
 */
public interface ModelClass<T> extends ModelComponent<T>, Iterable<ModelComponent<T>> {

    /**
     * Retrieve a child field property of this component by its name or alias.
     *
     * Similar to {@link ModelComponent#getComponent(String) } but ignores
     * fields.
     *
     * @param alias field name or alias
     * @return the field model or null if not found
     */
    ModelField<T, ?> getField(String alias);

}
