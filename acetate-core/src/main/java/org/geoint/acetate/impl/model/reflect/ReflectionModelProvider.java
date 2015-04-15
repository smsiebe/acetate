package org.geoint.acetate.impl.model.reflect;

import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.spi.model.ClassDataModelProvider;

/**
 * Model provider which uses reflection to resolve the data model.
 *
 * This is the default {@link ClassDataModelProvider}.
 */
public final class ReflectionModelProvider implements ClassDataModelProvider {

    @Override
    public <T> DataModel<T> model(Class<T> clazz) throws ModelException {
        return ReflectionDataModel.model(clazz);
    }

}
