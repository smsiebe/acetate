package org.geoint.acetate.impl.bind;

import org.geoint.acetate.bind.FieldBinder;
import org.geoint.acetate.codec.AcetateTransformException;
import org.geoint.acetate.metamodel.FieldModel;

/**
 * Binds a data field to the model.
 *
 * @param <D> object type to be bound
 * @param <F> the value of the field
 * @param <T> the value of the field after encoding
 */
public class DefaultFieldBinder<D, F, T> implements FieldBinder<D, F, T> {

    private final FieldModel<D, F, T> model;
    private final D dataItem;

    public DefaultFieldBinder(FieldModel<D, F, T> model, D dataItem) {
        this.model = model;
        this.dataItem = dataItem;
    }

    @Override
    public FieldModel<D, F, T> getField() {
        return model;
    }

    @Override
    public F get() {
        if (dataItem == null) {
            return null;
        }
        return model.getAccessor().get(dataItem);
    }

    @Override
    public T getEncoded() throws AcetateTransformException {
        return (model.getCodec() != null) ? model.getCodec().encode(get()) : null;
    }

}
