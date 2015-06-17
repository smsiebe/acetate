package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.Domain;

/**
 * Defines the contextual model for a Map of data.
 */
@Domain(name = "acetate", version = 1)
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
