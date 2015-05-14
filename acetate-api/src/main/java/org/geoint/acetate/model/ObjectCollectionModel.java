package org.geoint.acetate.model;

/**
 * An model component which may contain zero or more object instances of the
 * type defined by the collection model.
 *
 * @param <T> collection data type
 */
public interface ObjectCollectionModel<T> extends ContextualComponent {

    ContextualObjectModel<T> getCollectionModel();
}
