package org.geoint.acetate.metamodel;

/**
 * Model which contain one or more child model components.
 *
 * @param <T> object type this model represents
 */
public interface ModelClass<T> extends ModelComponent<T>, Iterable<ModelComponent<T>> {

    /**
     * Retrieve a field of this class by its relative name or alias (not full
     * path).
     *
     * @param alias field name or alias
     * @return matching field or null if no field matches
     */
    ModelComponent<T> getComponent(String alias);

}
