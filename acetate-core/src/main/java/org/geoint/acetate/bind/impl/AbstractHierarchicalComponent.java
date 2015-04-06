package org.geoint.acetate.bind.impl;

import java.util.Collection;
import java.util.LinkedList;
import org.geoint.acetate.bound.BoundComponent;
import org.geoint.acetate.model.ComponentModel;

/**
 *
 * @param <M> component model type
 */
public class AbstractHierarchicalComponent<M extends ComponentModel>
        implements BoundComponent<M> {

    private final M model;
    private final Collection<BoundComponent> components;

    public AbstractHierarchicalComponent(M model) {
        this.model = model;
        components = new LinkedList<>();
    }

    @Override
    public M getModel() {
        return model;
    }

    public Collection<BoundComponent> getComponents() {
        return components;
    }

}
