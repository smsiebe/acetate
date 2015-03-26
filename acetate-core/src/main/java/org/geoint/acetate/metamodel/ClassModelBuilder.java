package org.geoint.acetate.metamodel;

import org.geoint.concurrent.NotThreadSafe;

/**
 * Build a new class (aggregate).
 *
 * @param <T> data type of the class
 */
@NotThreadSafe
public class ClassModelBuilder<T> extends ComponentModelBuilder<T> {

    public ClassModelBuilder(String name) {
        super(name);
    }

    @Override
    public ModelClass<T> build() {

    }

}
