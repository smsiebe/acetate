package org.geoint.acetate.model.builder;

import java.util.Set;
import org.geoint.acetate.impl.model.ImmutableComponentAddress.ImmutableComponentAddress;
import org.geoint.acetate.model.DomainCompositeObject;
import org.geoint.acetate.model.DomainModel;

/**
 * Programmatic API to define a domain object which is in the context of another
 * domain object.
 *
 * This builder defines the <i>contextual</i> characteristics of the object,
 * which inherits from the "base" domain model object. This means that all
 * objects must be defined with the domain model builder, not just in context.
 *
 * @param <T> component type
 */
public class DomainCompositeObjectBuilder<T>
        extends AbstractContextualObjectBuilder<T, DomainCompositeObjectBuilder<T>> {

    public DomainCompositeObjectBuilder(ImmutableComponentAddress path,
            String baseComponentName, boolean isCollection) {
        super(path, baseComponentName, isCollection);
    }

    @Override
    public DomainCompositeObject<T> build(DomainModel model) {

    }

    @Override
    protected DomainCompositeObjectBuilder<T> self() {
        return this;
    }

    @Override
    public Set<String> getDependencies() {
    }

}
