package org.geoint.acetate.entity.model;

import org.geoint.acetate.meta.annotation.Model;

/**
 * Defines the contextual model for a single data model instance.
 */
@Model(name = "instance", domainName = "acetate", domainVersion = 1)
public interface ContextualInstanceModel extends ContextualDataModel {

    /**
     * The model of this contextual model.
     *
     * @return data model for this contextual component
     */
    DataModel<?> getDataModel();
}
