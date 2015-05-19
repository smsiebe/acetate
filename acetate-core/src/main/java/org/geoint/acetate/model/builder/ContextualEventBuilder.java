package org.geoint.acetate.model.builder;

import java.util.Set;
import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableObjectPath;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.event.DomainEntityEvent;

/**
 * Programmatic API to override the contextual traits of an Entity Event.
 *
 * @param <T> object representation of the domain event
 * @param <E> domain entity object type
 */
public class ContextualEventBuilder<T, E>
        extends AbstractContextualComponentBuilder<T, ContextualEventBuilder> {

    protected final String eventDomainObjectName;

    public ContextualEventBuilder(ImmutableObjectPath path,
            String eventDomainObjectName) {
        super(path);
        this.eventDomainObjectName = eventDomainObjectName;
    }

    @Override
    public DomainEntityEvent<T> build(DomainModel model) {

    }

    @Override
    protected ContextualEventBuilder<T, E> self() {
        return this;
    }

    @Override
    public Set<String> getDependencies() {
    }

}
