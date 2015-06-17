package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.Domain;

/**
 * Defines the contextual model for a Collection of data.
 */
@Domain(name = "acetate", version = 1)
public interface ContextualCollectionModel extends ContextualDataModel {

    /**
     * The data mode of the collection content.
     *
     * @return data model for the contents of the collection
     */
    ContextualDataModel getCollectionModel();
}
