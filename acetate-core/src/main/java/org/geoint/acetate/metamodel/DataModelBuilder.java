package org.geoint.acetate.metamodel;

import org.geoint.acetate.bind.BoundData;
import org.geoint.concurrent.NotThreadSafe;
import org.geoint.util.hierarchy.Hierarchy;
import org.geoint.util.hierarchy.impl.StandardHierarchy;
import org.geoint.util.hierarchy.impl.StandardHierarchyBuilder;

/**
 * API to programmatically create a data model.
 *
 * @param <T> type of object that is intended to be bound to this model
 */
@NotThreadSafe
public final class DataModelBuilder<T> {

    private final StandardHierarchyBuilder<ComponentModelBuilder> model;

    public DataModelBuilder(String name) {
        this.model.child(name).withValue(new ClassModelBuilder<>(name));
    }

    public ClassModelBuilder<T> model() {
        return root;
    }

    public DataModel<T> build() {
        return DataModelImpl.from(root);
    }

    private static class DataModelImpl<T> implements DataModel<T> {

        private final StandardHierarchy<ModelComponent<?>> model;

        private DataModelImpl(StandardHierarchy<ModelComponent<?>> model) {
            this.model = model;
        }

        public static <T> DataModel<T> from(ClassModelBuilder<T> rootBuilder) {
            
            //build a Hierarchy from the model
            StandardHierarchyBuilder<ModelComponent<?>> b 
                    = StandardHierarchy.builder();

            return new DataModelImpl();
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
