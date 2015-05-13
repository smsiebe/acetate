package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import org.geoint.acetate.model.ComponentContext;
import org.geoint.acetate.model.ComponentModel;
import org.geoint.acetate.model.ComponentOperation;
import org.geoint.acetate.model.registry.ComponentRegistry;

/**
 *
 */
public class ImmutableComponentModel<T> implements ComponentModel<T> {

    private final String componentName;
    private final Optional<Class<T>> dataType;
    private final ImmutableComponentContext context;
    private final Collection<ImmutableComponentOperation> operations;
    private final ComponentRegistry registry;

    public ImmutableComponentModel(String componentName,
            ImmutableComponentContext context,
            Collection<ImmutableComponentOperation> operations,
            ComponentRegistry registry) {
        this(componentName, null, context, operations, registry);
    }

    public ImmutableComponentModel(String componentName,
            Class<T> dataType,
            ImmutableComponentContext context,
            Collection<ImmutableComponentOperation> operations,
            ComponentRegistry registry) {
        this.componentName = componentName;
        this.dataType = Optional.ofNullable(dataType);
        this.context = context;
        this.operations = operations;
        this.registry = registry;
    }

    @Override
    public String getComponentName() {
        return componentName;
    }

    @Override
    public Optional<Class<T>> getDataType() {
        return dataType;
    }

    @Override
    public ComponentContext getContext() {
        return context;
    }

    @Override
    public Collection<? extends ComponentOperation> getOperations() {
        return operations;
    }

    @Override
    public String toString() {
        return String.join("@", componentName, context.toString());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.componentName);
        hash = 97 * hash + Objects.hashCode(this.context);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImmutableComponentModel<?> other = (ImmutableComponentModel<?>) obj;
        if (!Objects.equals(this.componentName, other.componentName)) {
            return false;
        }
        if (!Objects.equals(this.context, other.context)) {
            return false;
        }
        return true;
    }

}
