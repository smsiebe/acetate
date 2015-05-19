package org.geoint.acetate.model.builder;

import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableObjectPath;
import org.geoint.acetate.model.DomainComponent;
import org.geoint.acetate.model.DomainModel;

/**
 * Programmatic API to override the contextual traits of an Entity Event.
 *
 * @param <T>
 */
public class ContextualEventBuilder<T>
        extends AbstractContextualComponentBuilder<T, ContextualEventBuilder<T>> {

    public ContextualEventBuilder(ImmutableObjectPath path,
            String baseComponentName) {
        super(path, baseComponentName, false);
    }

    @Override
    public DomainComponent<T> build(DomainModel model) {

    }

    @Override
    protected ContextualEventBuilder<T> self() {
        return this;
    }

}
