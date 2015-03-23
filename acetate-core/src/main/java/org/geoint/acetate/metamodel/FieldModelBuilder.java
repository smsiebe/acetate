package org.geoint.acetate.metamodel;

import org.geoint.acetate.metamodel.DataModelBuilder.ComponentModelBuilder;

/**
 *
 * @param <P>
 * @param <F>
 */
public class FieldModelBuilder<P, F>
        extends ComponentModelBuilder<F, ModelField<P, F>> {

    public FieldModelBuilder(String name) {
        super(name);
    }

    @Override
    protected ModelField<P, F> build() {

    }

}
