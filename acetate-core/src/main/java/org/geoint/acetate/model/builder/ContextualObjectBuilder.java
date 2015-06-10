package org.geoint.acetate.model.builder;

import java.util.Set;
import org.geoint.acetate.impl.model.ImmutableComponentAddress.ImmutableComponentAddress;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainObject;

/**
 *
 * @param <T>
 */
public class ContextualObjectBuilder<T>
        extends AbstractContextualObjectBuilder<T, ContextualObjectBuilder<T>> {

    public ContextualObjectBuilder(ImmutableComponentAddress path,
            String baseDomainName, boolean isCollection) {
        super(path, baseDomainName, isCollection);
    }

    @Override
    public DomainObject<T> build(DomainModel model) {

    }

    @Override
    protected ContextualObjectBuilder<T> self() {
        return this;
    }

    @Override
    public Set<String> getDependencies() {
    }

}
