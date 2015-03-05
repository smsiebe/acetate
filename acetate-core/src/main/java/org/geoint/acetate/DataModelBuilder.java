package org.geoint.acetate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.geoint.acetate.bind.BoundData;
import org.geoint.acetate.codec.AcetateCodec;
import org.geoint.acetate.metamodel.FieldAccessor;
import org.geoint.acetate.metamodel.ModelField;
import org.geoint.acetate.metamodel.FieldSetter;
import org.geoint.acetate.metamodel.ModelClass;
import org.geoint.acetate.metamodel.ModelComponent;
import org.geoint.acetate.metamodel.MutableModel;
import org.geoint.util.hierarchy.HierarchicalPath;
import org.geoint.util.hierarchy.LinkedHierarchy;
import org.geoint.util.hierarchy.MutableHierarchy;

/**
 * MutableDataModel implementation for creating new objects.
 *
 * Binding with a programmatically created data model will always result in a
 * {@link BoundData<?>} object being returned from reads, rather than a specific
 * object instance.
 *
 * @param <R> root java object type of the data model
 */
public class DataModelBuilder<R extends BoundData<?>> implements MutableModel<R> {

    private final MutableHierarchy<ModelComponent<?>> components
            = new LinkedHierarchy<>();

    @Override
    public <P, T> ModelField<P, T> setField(String path, FieldAccessor<P, T> accessor,
            FieldSetter<P, T> setter) {
        HierarchicalPath p = HierarchicalPath.instance(path);
        FieldModelBuilder<P, T> model = new FieldModelBuilder<>(
                p.getNodeName(), accessor, setter);
        components.put(path, model);
        return model;
    }

    @Override
    public ModelComponent<?> removeComponent(String path) {
        return components.remove(HierarchicalPath.instance(path));
    }

    @Override
    public ModelComponent<?> addAlias(String name, String... aliases) {
        if (aliases == null || aliases.length == 0) {
            return null;
        }

        final HierarchicalPath p = HierarchicalPath.instance(name);
        final ModelComponent<?> component = components.getValue(p);

        if (component == null) {
            return null;
        }

        final HierarchicalPath parent = p.getParent();
        Arrays.stream(aliases)
                .parallel()
                .map((a) -> parent.getChild(a))
                .forEach((cp) -> components.put(cp, component));

        return component;
    }

    @Override
    public ModelField setCodec(String componentPath, AcetateCodec codec) {

    }

    @Override
    public void addCodec(AcetateCodec codec) {

    }

    @Override
    public ModelField getField(String path) {

    }

    @Override
    public ModelClass getModel() {

    }

    @Override
    public BoundData<R> bind(R instance) {

    }

    private class BuilderDataModel<BoundData> {

    }

    private class FieldModelBuilder<P, F> implements ModelField<P, F> {

        private final String name;
        private final Set<String> aliases = new HashSet<>();
        private final FieldAccessor<P, F> accessor;
        private final FieldSetter<P, F> setter;

        public FieldModelBuilder(String name, FieldAccessor<P, F> accessor,
                FieldSetter<P, F> setter, String... aliases) {
            this.name = name;
            this.accessor = accessor;
            this.setter = setter;
            this.aliases.addAll(Arrays.asList(aliases));
        }

        @Override
        public Set<String> getAliases() {
            return aliases;
        }

        @Override
        public FieldAccessor<P, F> getAccessor() {
            return accessor;
        }

        @Override
        public FieldSetter<P, F> getSetter() {
            return setter;
        }

        @Override
        public String getName() {
            return name;
        }

    }
}
