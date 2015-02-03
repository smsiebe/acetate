package org.geoint.acetate.impl.bind;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.geoint.acetate.bind.BoundData;
import org.geoint.acetate.bind.BoundField;
import org.geoint.acetate.metamodel.DataModel;

/**
 *
 */
public class DefaultDataBinder<F> implements BoundData<F> {

    private final DataModel<F> model;
    private final F instance;
    private Map<String, String> unmatched;
    private Map<String, BoundField<F, ?, ?>> fields;

    public DefaultDataBinder(DataModel<F> model, F instance) {
        this.model = model;
        this.instance = instance;
    }

    @Override
    public F get() {
        return instance;
    }

    @Override
    public DataModel<F> getModel() {
        return model;
    }

    @Override
    public Map<String, String> getUnmatched() {
        return unmatched;
    }

    @Override
    public Collection<BoundField<F, ?, ?>> getFields() {
        return new ArrayList<>(fields.values());
    }

}
