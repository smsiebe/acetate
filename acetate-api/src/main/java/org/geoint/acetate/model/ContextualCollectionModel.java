package org.geoint.acetate.model;

/**
 * An model component which may contain zero or more object instances of the
 * type defined by the collection model.
 *
 * @param <T> collection data type
 */
public interface ContextualCollectionModel<T> extends ContextualModelComponent<T> {

    ContextualObjectModel<T> getCollectionModel();
}
