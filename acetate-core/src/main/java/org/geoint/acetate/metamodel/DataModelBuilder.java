package org.geoint.acetate.metamodel;

import org.geoint.acetate.bind.BoundData;
import org.geoint.concurrent.NotThreadSafe;
import org.geoint.util.hierarchy.Hierarchy;

/**
 * API to programmatically create a data model.
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
        return DataModelImpl.from(root);
    }

    protected static class DataModelImpl<T> implements DataModel<T> {

        private final StandardHierarchy<ModelComponent<?>> root;

        public DataModelImpl(ModelClass<?> root) {
            this.root = root;
        }

        public static <T> DataModel<T> from(ClassModelBuilder<T> builder) {
            return new DataModelImpl(builder.build());
        }

        @Override
        public BoundData<T> bind(T instance) {

        }

        @Override
        public ModelComponent<?> getComponent(String path) {

        }

        @Override
        public ModelClass<T> getModel() {

        }
    }

}
