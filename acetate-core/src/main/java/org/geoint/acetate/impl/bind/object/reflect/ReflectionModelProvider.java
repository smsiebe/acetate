package org.geoint.acetate.impl.bind.object.reflect;

import org.geoint.acetate.impl.model.MutableModelDecorator;
import org.geoint.acetate.metamodel.DataModel;
import org.geoint.acetate.metamodel.ModelException;
import org.geoint.acetate.metamodel.MutableModel;
import org.geoint.acetate.spi.model.ModelProvider;

/**
 * Model provider which uses reflection to resolve the data model.
 *
 * This is the default {@link ModelProvider}.
 */
public final class ReflectionModelProvider implements ModelProvider {

    @Override
    public <T> DataModel<T> model(Class<T> clazz) throws ModelException {
        return ReflectionDataModel.from(clazz);
    }

    @Override
    public <T> MutableModel<T> mutableModel(Class<T> clazz) throws ModelException {
        return new MutableModelDecorator(model(clazz));
    }

}
