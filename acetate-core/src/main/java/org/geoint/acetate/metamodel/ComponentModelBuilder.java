package org.geoint.acetate.metamodel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.geoint.concurrent.NotThreadSafe;

/**
 *
 * @param <T>
 */
@NotThreadSafe
public abstract class ComponentModelBuilder<T> {

    private final String name;
    private final Set<String> aliases; //alies for THIS component
    private final Map<String, ? extends ClassModelBuilder<?>> aggregates;
    //all components (aggregates and fields), including aliases
    //used as an in-memory index
    private final Map<String, ComponentModelBuilder> components;

    public ComponentModelBuilder(String name) {
        this.name = name;
        this.aggregates = new HashMap<>();
        this.components = new HashMap<>();
        this.aliases = new HashSet<>();
    }

    /**
     * Adds an alias for this component.
     *
     * @param alias additional alias name for this component
     * @return this builder (fluid interface)
     */
    public ComponentModelBuilder<T> withAlias(String alias) {
        aliases.add(alias);
        return this;
    }

    /**
     * Returns the builder for the specified model aggregate, creating a new one
     * if needed.
     *
     * <b>NOTE</b>: The builder returned from this method is that of the new
     * aggregate, not this model.
     *
     * @param name local model unique component name
     * @return builder for this aggregate
     */
    public ClassModelBuilder<?> aggregate(String name) {
        return getOrCreateComponent(name, (n) -> new ClassModelBuilder(n));
    }

    /**
     * Returns the builder for the specified model aggregate, creating a new one
     * if needed.
     *
     * <b>NOTE</b>: The builder returned from this method is that of the new
     * aggregate, not this model.
     *
     * @param <T> aggregate type
     * @param name local model unique component name
     * @param type class type of the aggregate
     * @return builder for this aggregate
     */
    public <T> ComponentModelBuilder<T> aggregate(String name, Class<T> type) {
        return getOrCreateComponent(name, (n) -> new ClassModelBuilder<T>(n));
    }

    /**
     * Adds a new component to the builder.
     *
     * This method must be called by all subclasses that with to add a new,
     * perhaps specialized, component.
     *
     * @param <M> component model builder type that is created
     * @param name name of the component
     * @param factory factory to builder a new component as needed
     * @return the component builder for this component
     */
    protected final <M extends ComponentModelBuilder<?>>
            M getOrCreateComponent(String name, ComponentBuilderFactory<M> factory) {

        if (components.containsKey(name)) {
            return (M) components.get(name);
        }

        M mb = factory.create(name);
        this.components.put(name, mb);
        return mb;
    }

    /**
     * Functional interface used to asynchronously create a
     * ComponentModelBuilder after it's determined that the builder can be made.
     *
     * @param <B> component builder type
     */
    @FunctionalInterface
    protected static interface ComponentBuilderFactory<B extends ComponentModelBuilder> {

        B create(String name);
    }
}
