package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.Domain;

/**
 * When in context of another model component, a data model may be defined as a
 * collection, or as the key/value of a map, or simply as itself. The
 * specialized ContextualDataModel type provides the context-specific details on
 * how data may be represented.
 *
 * @see ContextualMapModel
 * @see ContextualCollectionModel
 * @see ContextualInstanceModel
 */
@Domain(name = "acetate", version = 1)
public interface ContextualDataModel extends ContextualModel {

}
