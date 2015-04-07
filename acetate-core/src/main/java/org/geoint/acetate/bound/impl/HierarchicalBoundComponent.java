package org.geoint.acetate.bound.impl;

import java.util.Collection;
import java.util.LinkedList;
import org.geoint.acetate.bound.BoundComponent;
import org.geoint.acetate.model.ComponentModel;
import org.geoint.concurrent.ThreadSafe;

/**
 *
 * @param <M> component model type
 */
@ThreadSafe
public class HierarchicalBoundComponent<M extends ComponentModel>
        implements BoundComponent<M> {

    private final M model;
    private final Collection<BoundComponent> components;

    public HierarchicalBoundComponent(M model) {
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
