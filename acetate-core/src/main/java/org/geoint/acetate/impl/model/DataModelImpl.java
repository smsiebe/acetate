package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.geoint.acetate.model.ComponentModel;
import org.geoint.acetate.model.DomainModel;

/**
 * Immutable data model.
 *
 * Thread safe.
 */
public class DataModelImpl implements DomainModel {

    //key is unique component id
    private final Map<String, ComponentModel> components;

    private DataModelImpl(Map<String, ComponentModel> components) {
        this.components = Collections.unmodifiableMap(components);
    }

    @Override
    public Collection<ComponentModel> getComponents() {
        return components.values();
    }

}
