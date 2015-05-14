package org.geoint.acetate.model;

/**
 * Marker interface which identifies a component which may only be used in
 * context, and is not itself a "base" domain model component.
 *
 * @see ContextualModelComponent
 * @see ContextualObjectModel
 * @see ContextualOperationModel
 * @see ObjectCollectionModel
 * @see ObjectMapModel
 */
public interface ContextualComponent extends ModelComponent, Inheritable {

}
