package org.geoint.acetate.metamodel;

import java.util.Iterator;
import java.util.Set;
import org.geoint.acetate.metamodel.DataModelBuilder.ComponentModelBuilder;
import org.geoint.concurrent.NotThreadSafe;

/**
 *
 * @param <T>
 */
@NotThreadSafe
public class ClassModelBuilder<T> extends ComponentModelBuilder<T, ModelClass<T>> {

    public ClassModelBuilder(String name) {
        super(name);
    }

    @Override
    protected ModelClass<T> build() {

    }

    private static class ModelClassImpl<T> implements ModelClass<T> {

        @Override
        public ModelComponent<T> getComponent(String alias) {

        }

        @Override
        public String getName() {

        }

        @Override
        public Set<String> getAliases() {

        }

        @Override
        public Iterator<ModelComponent<T>> iterator() {

        }

    }
}
