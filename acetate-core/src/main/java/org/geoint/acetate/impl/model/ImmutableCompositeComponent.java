package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.geoint.acetate.model.ComponentOperation;
import org.geoint.acetate.model.CompositeComponent;
import org.geoint.acetate.model.CompositeComponentModel;
import org.geoint.acetate.model.registry.ComponentRegistry;

/**
 *
 * @param <T>
 */
public class ImmutableCompositeComponent<T>
        extends ImmutableComponentModel<T>
        implements CompositeComponentModel<T> {

    private final Map<String, ImmutableCompositeComponent> components;

    public ImmutableCompositeComponent(String componentName,
            ImmutableComponentContext context,
            Collection<ImmutableCompositeComponent> operations,
            ComponentRegistry registry,
            Collection<ImmutableCompositeComponent> components) {
        super(componentName, context, operations, registry);
        this.components = Collections.unmodifiableMap(
                components.stream()
                .collect(Collectors.toMap(
                                (cc) -> cc.getLocalName(),
                                (cc) -> cc)
                )
        );
    }

    public ImmutableCompositeComponent(String componentName,
            Class<T> dataType, ImmutableComponentContext context,
            Collection<ComponentOperation> operations,
            ComponentRegistry registry,
            Map<String, ImmutableCompositeComponent> components) {
        super(componentName, dataType, context, operations, registry);
        this.components = components;
    }

    @Override
    public Set<String> getCompositeNames() {
        return components.keySet();
    }

    @Override
    public Collection<CompositeComponent> getComposites() {
        return components.values();
    }

}
