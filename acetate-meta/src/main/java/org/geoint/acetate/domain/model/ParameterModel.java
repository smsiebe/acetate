package org.geoint.acetate.domain.model;

import org.geoint.acetate.domain.annotation.Object;

/**
 * Model of an Operation parameter.
 *
 */
@Object(name="operationParameter", domainName="acetate", domainVersion="1.0-BETA")
public interface ParameterModel {

    String getName();

    ObjectModel getModel();

}
