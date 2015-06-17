package org.geoint.acetate.model;

import java.util.Collection;
import org.geoint.acetate.model.annotation.Model;

/**
 * Models behavior of an Entity.
 *
 */
@Model(name="", domainName="acetate", domainVersion=1)
public interface OperationModel extends ModelComponent {

    /**
     * Model mode of the returned type.
     *
     * @return model of the returned type from the method or null if returns
     * void
     */
    ReturnModel getReturnModel();

    /**
     * Model models of the operation parameters, in declaration order.
     *
     * @return models of the operation parameters in declaration order; empty
     * collection if no parameters are required for the operation
     */
    Collection<ParameterModel> getParameterModels();

}
