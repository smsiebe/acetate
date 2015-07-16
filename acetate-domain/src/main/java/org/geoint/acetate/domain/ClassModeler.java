package org.geoint.acetate.domain;

import org.geoint.acetate.domain.annotation.Service;
import org.geoint.acetate.domain.model.DataModel;
import org.geoint.acetate.domain.model.ModelException;

/**
 * Programmatically creates a DataModel definition from a class.
 */
@FunctionalInterface
@Service(name = "classModeler", displayName = "Class Modeler",
        domain = DataModel.ACETATE_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public interface ClassModeler {

    <T> DataModel<T> model(Class<T> type) throws ModelException;
}
