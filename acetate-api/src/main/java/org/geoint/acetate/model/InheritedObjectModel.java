package org.geoint.acetate.model;

/**
 * A domain model object which inherits from one or more other domain model
 * objects.
 *
 * @param <T> object data type
 */
public interface InheritedObjectModel<T>
        extends ObjectModel<T>, Inherited<ObjectModel<? super T>> {

}
