package org.geoint.acetate.impl.structure;

import java.util.HashMap;
import java.util.Map;
import org.geoint.acetate.impl.model.ClassModelBuilder;
import org.geoint.acetate.impl.model.DataModelBuilder;
import org.geoint.acetate.impl.model.ValueModelBuilder;
import org.geoint.acetate.structure.DataStructure;
import org.geoint.acetate.model.ModelException;
import org.geoint.concurrent.NotThreadSafe;
import org.geoint.util.function.Creator;

/**
 * Programmatic construction of a DataStructure (model + data).
 *
 * @param <T> corresponding Java data type of the model
 */
@NotThreadSafe
public final class DataStructureBuilder<T> {

    
    //flat model for classes, key is full path
    private final Map<String, ClassModelBuilderImpl<?>> classComponents;
    //key = alias; value = component name
    private final Map<String, String> reverseAliases;

    public DataStructureBuilder() {
        classComponents = new HashMap<>();
        reverseAliases = new HashMap<>();
    }

    public <C> ClassModelBuilder<C> addClass(String name, Class<C> type)
            throws ModelException {
        return addComponent(name, type, classComponents, valueComponents,
                () -> new ClassModelBuilderImpl(type, name));
    }

    public <V> ValueModelBuilder<V> addValue(String name, Class<V> type)
            throws ModelException {
        return addComponent(name, type, valueComponents, classComponents,
                () -> new ValueModelBuilderImpl(type, name));
    }

    public DataStructureBuilder<T> aliasComponent(String name, String alias)
            throws ModelException {

        if (reverseAliases.containsKey(alias)) {
            final String componentKey = reverseAliases.get(alias);
            DataModelBuilderImpl b = (classComponents.containsKey(componentKey)
                    ? classComponents.get(componentKey)
                    : valueComponents.get(componentKey));
            throw new ModelException(b.type, "Cannot add component named '"
                    + name + "' "
                    + "to model, a component already exists with this "
                    + "name in the model.");
        }

        reverseAliases.put(alias, name);
        return this;
    }

    /*
     * centralized add component method logic using method references
     */
    private <C, B extends DataModelBuilder<C, B>> B addComponent(
            String name,
            Class<C> type,
            Map<String, DataModelBuilderImpl> mayExist,
            Map<String, DataModelBuilderImpl> notExist,
            Creator<DataModelBuilderImpl> creator)
            throws ModelException {

        if (notExist.containsKey(name)) {
            throw new ModelException(type, "Cannot add component named '"
                    + name + "' "
                    + "to model, a component already exists with this "
                    + "name in the model.");
        }

        if (mayExist.containsKey(name)) {
            DataModelBuilderImpl cb = mayExist.get(name);

            if (!cb.type.equals(type)) {
                throw new ModelException(type, "Cannot add component named '"
                        + name + "' to model, a component of type '"
                        + cb.type.getName() + "' is already registered to the"
                        + " model with this name.");
            }

            return (B) mayExist.get(name);
        }

        //doesn't exist, create the builder
        DataModelBuilderImpl cb = creator.instance();

        mayExist.put(name, cb);
        return (B) cb;
    }

    public DataStructure build() {

    }

    private abstract class DataModelBuilderImpl<T, B extends DataModelBuilderImpl<T, B>>
            implements DataModelBuilder<T, B> {

        private final Class<T> type;
        private final String name;

        public DataModelBuilderImpl(Class<T> type, String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public B alias(String alias) throws ModelException {
            aliasComponent(name, alias);
            return builder();
        }

        abstract protected B builder();

    }

    private class ClassModelBuilderImpl<T>
            extends DataModelBuilderImpl
            implements ClassModelBuilder {

        public ClassModelBuilderImpl(Class<T> type, String name) {
            super(type, name);
        }

        @Override
        protected ClassModelBuilderImpl<T> builder() {
            return this;
        }

    }

    private class ValueModelBuilderImpl<T>
            extends DataModelBuilderImpl
            implements ValueModelBuilder {

        public ValueModelBuilderImpl(Class<T> type, String name) {
            super(type, name);
        }

        @Override
        protected ValueModelBuilderImpl<T> builder() {
            return this;
        }

    }
}
