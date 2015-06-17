package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.Domain;

/**
 * Model of an operation parameter.
 */
@Domain(name = "acetate", version = 1)
public interface ParameterModel extends ContextualModel {

    /**
     * Name of the operation parameter.
     *
     * @return parameter name
     */
    String getParameterName();

    /**
     * Contextual data model of the parameter.
     *
     * @return parameter contextual data model
     */
    ContextualDataModel getDataModel();

}
