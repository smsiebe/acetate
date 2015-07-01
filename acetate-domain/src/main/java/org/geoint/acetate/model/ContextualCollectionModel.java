package org.geoint.acetate.model;

import org.geoint.acetate.meta.annotation.Model;

/**
 * Defines the contextual model for a Collection of data.
 */
@Model(name="collection", domainName="acetate", domainVersion=1)
public interface ContextualCollectionModel extends ContextualDataModel {

    /**
     * The data mode of the collection content.
     *
     * @return data model for the contents of the collection
     */
    ContextualDataModel getCollectionModel();
}
