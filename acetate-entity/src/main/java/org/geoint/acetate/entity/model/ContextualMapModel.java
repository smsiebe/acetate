package org.geoint.acetate.entity.model;

import org.geoint.acetate.meta.annotation.Model;

/**
 * Defines the contextual model for a Map of data.
 */
@Model(name = "map", domainName = "acetate", domainVersion = 1)
public interface ContextualMapModel extends ContextualDataModel {

    /**
     * The contextual data model of the map key.
     *
     * This method returns a contextual data model as the key of a map may, for
     * example, be another map or collection.
     *
     * @return key model
     */
    ContextualDataModel getKeyModel();

    /**
     * The contextual data model of the map value.
     *
     * @return value model
     */
    ContextualDataModel getValueModel();
}
