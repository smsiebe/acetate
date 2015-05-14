package org.geoint.acetate.model;

/**
 * A domain model object which is defined in context, such as as a composite of
 * another domain model object, as a return/parameter type of an operation, etc.
 *
 * Unlike a regular domain model object which may inherit from other domain
 * model object, the contextual object model should be considered "flat" - a
 * snapshot of the object model in context.
 *
 * @param <T> object type
 * @see ObjectModel
 * @see InheritedObjectModel
 */
public interface ContextualObjectModel<T>
        extends ContextualModelComponent<ObjectModel<T>>, ObjectModel<T> {

}
