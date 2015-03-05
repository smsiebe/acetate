package org.geoint.acetate.impl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.geoint.acetate.bind.BoundData;
import org.geoint.acetate.codec.AcetateCodec;
import org.geoint.acetate.metamodel.DataModel;
import org.geoint.acetate.metamodel.FieldAccessor;
import org.geoint.acetate.metamodel.ModelField;
import org.geoint.acetate.metamodel.FieldSetter;
import org.geoint.acetate.metamodel.MutableModel;

/**
 * Provides a "mutable" model layer on top of a DataModel, allowing for 
 * changes to the resultant model but without changes to the underlying 
 * DataModel.
 *
 * This is not thread safe.
 *
 * @param <T>
 */
public final class MutableModelDecorator<T> implements MutableModel<T> {

    private final DataModel<T> baseModel;
    //key is a field alias name, the map value is a ref to the field model
    private final Map<String, DefaultFieldModel<?, ?>> fields;
    private final Map<DefaultFieldModel<?, ?>, Collection<AcetateCodec<?, ?>>> codecs;

    public MutableModelDecorator(DataModel<T> baseModel) {
        this.baseModel = baseModel;
        this.fields = new HashMap<>();
        this.codecs = new HashMap<>();
    }

    @Override
    public <P, T> ModelField<?, ?> setField(String name,
            FieldAccessor<P, T> accessor,
            FieldSetter<P, T> setter) {
        DefaultFieldModel<?, ?> model = new DefaultFieldModel(accessor, setter, name);
        return setField(model);
    }

    @Override
    public ModelField<?, ?> removeComponent(String name) {
        ModelField<?, ?> removed = fields.remove(name);

        if (removed != null) {
            //remove all aliases/references
            removed.getNames().stream().forEach((a) -> fields.remove(a));
        }

        return removed;
    }

    @Override
    public ModelField<?, ?> addAlias(String name, String... newAliases) {
        DefaultFieldModel<?, ?> model = fields.get(name);
        if (model != null) {
            model = DefaultFieldModel.addAliases(model, newAliases);
            setField(model);
        }
        return model;
    }

}
