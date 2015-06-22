package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.Model;

/**
 * Model of an operation parameter.
 */
@Model(name="parameter", domainName="acetate", domainVersion=1)
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
