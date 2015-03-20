package org.geoint.acetate.metamodel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.geoint.concurrent.NotThreadSafe;

/**
 * Programmatic creation of a DataModel.
 *
 * @param <T> type of object that is intended to be bound to this model
 */
@NotThreadSafe
public class DataModelBuilder<T> {

    private final ClassModelBuilder<T> root;

    public DataModelBuilder(String name) {
        this.root = new ClassModelBuilder<>(name);
    }

    public ClassModelBuilder<T> model() {
        return root;
    }

    public DataModel<T> build() {

    }

    @NotThreadSafe
    abstract class ComponentModelBuilder<T> {

        private final String name;
        private final Set<String> aliases; //alies for THIS component
        private final Map<String, ClassModelBuilder<?>> aggregates;
        //all components (aggregates and fields), including aliases
        //used as an in-memory index
        private final Map<String, ? super ComponentModelBuilder<?>> components;

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
         * Returns the builder for the specified model aggregate, creating a new
         * one if needed.
         *
         * <b>NOTE</b>: The builder returned from this method is that of the new
         * aggregate, not this model.
         *
         * @param name local model unique component name
         * @return builder for this aggregate
         * @throws ModelComponentCollisionException thrown if the name is
         * already in use
         */
        public ComponentModelBuilder<?> aggregate(String name)
                throws ModelComponentCollisionException {
            return getOrCreateComponent(name, (n) -> new ClassModelBuilder(n));
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
        protected final <M extends ComponentModelBuilder<?>, F extends ComponentBuilderFactory<M>>
                M getOrCreateComponent(String name, F factory) {

//            if (isNameCollision(name)) {
//                throw new ModelComponentCollisionException(null, "Could not add "
//                        + "new component '" + name + "' to model, component "
//                        + "already exists with this name.");
//            }
            if (components.containsKey(name)) {
                return components.get(name);
            }

            M mb = factory.create(name);
            this.components.put(name, mb);
            return mb;
        }

        /**
         * Determines if there is a component name collision.
         *
         * @param componentName component name to check for a collision
         * @return true if there is a name collision, otherwise false
         */
        private boolean isNameCollision(String componentName) {

        }
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

    /**
     *
     * @param <T> class type of the aggregate
     */
    @NotThreadSafe
    public static class ClassModelBuilder<T> extends ComponentModelBuilder<T> {

        public ClassModelBuilder(String name) {
            super(name);
        }

    }
}
