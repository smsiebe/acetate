package org.geoint.acetate.model.builder;

import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableObjectPath;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainObject;

/**
 *
 * @param <T>
 */
public class ContextualObjectBuilder<T>
        extends AbstractContextualObjectBuilder<T, ContextualObjectBuilder<T>> {

    public ContextualObjectBuilder(ImmutableObjectPath path,
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

}
