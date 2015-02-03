package org.geoint.acetate.impl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.geoint.acetate.codec.AcetateCodec;
import org.geoint.acetate.metamodel.DataModel;
import org.geoint.acetate.metamodel.FieldAccessor;
import org.geoint.acetate.metamodel.FieldModel;
import org.geoint.acetate.metamodel.FieldSetter;
import org.geoint.acetate.metamodel.MutableModel;

/**
 * Programmatically creates a data model.
 *
 * The builder is not thread safe, however, the model is once it is built.
 *
 * @param <F> object type this model creates
 */
public final class DataModelBuilder<F> implements MutableModel {

    private final Class<F> dataType;
    //key is a field alias name, the map value is a ref to the field model
    private final Map<String, DefaultFieldModel<?, ?>> fields;
    private final Map<DefaultFieldModel<?, ?>, Collection<AcetateCodec<?, ?>>> codecs;

    private DataModelBuilder(Class<F> dataType) {
        this.dataType = dataType;
        this.fields = new HashMap<>();
        this.codecs = new HashMap<>();
    }

    /**
     * Create a model builder that models for the specific object type.
     *
     * This method does not attempt to build a "base model" based on the class;
     * this creates a empty builder.
     *
     * @param <F> object type
     * @param dataType object type to model
     * @return model builder
     */
    public static <F> DataModelBuilder<F> forClass(Class<F> dataType) {
        DataModelBuilder<F> builder = new DataModelBuilder<>(dataType);
        return builder;
    }

    @Override
    public <P, T> FieldModel<?, ?> setField(String name,
            FieldAccessor<P, T> accessor,
            FieldSetter<P, T> setter) {
        DefaultFieldModel<?, ?> model = new DefaultFieldModel(accessor, setter, name);
        return setField(model);
    }

    @Override
    public FieldModel<?, ?> removeField(String name) {
        FieldModel<?, ?> removed = fields.remove(name);

        if (removed != null) {
            //remove all aliases/references
            removed.getNames().stream().forEach((a) -> fields.remove(a));
        }

        return removed;
    }

    @Override
    public FieldModel<?, ?> addAlias(String name, String... newAliases) {
        DefaultFieldModel<?, ?> model = fields.get(name);
        if (model != null) {
            model = DefaultFieldModel.addAliases(model, newAliases);
            setField(model);
        }
        return model;
    }

    @Override
    public FieldModel<?, ?> setCodec(String fieldName, AcetateCodec<?, ?> codec) {

    }

    @Override
    public void addCodec(AcetateCodec<?, ?> codec) {

    }

    public DataModel<F> build() {

    }

    private FieldModel<?, ?> setField(DefaultFieldModel<?, ?> model) {

        if (model != null) {
            //link all aliases to the new field model
            model.getNames().forEach((a) -> fields.put(a, model));
        }

        return model;
    }
}
