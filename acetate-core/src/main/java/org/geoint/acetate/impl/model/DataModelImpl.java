package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.geoint.acetate.model.DataComponent;
import org.geoint.acetate.model.DataModel;

/**
 * Immutable data model.
 *
 * Thread safe.
 */
public class DataModelImpl implements DataModel {

    //key is unique component id
    private final Map<String, DataComponent> components;

    private DataModelImpl(Map<String, DataComponent> components) {
        this.components = Collections.unmodifiableMap(components);
    }

    @Override
    public Collection<DataComponent> getComponents() {
        return components.values();
    }

}
