package org.geoint.acetate.model.builder;

import java.util.Set;
import org.geoint.acetate.impl.model.ImmutableObjectAddress.ImmutableComponentAddress;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.EventModel;

/**
 * Programmatic API to override the contextual traits of an Entity Event.
 *
 * @param <T> object representation of the domain event
 * @param <E> domain entity object type
 */
public class ContextualEventBuilder<T, E>
        extends AbstractContextualComponentBuilder<T, ContextualEventBuilder> {

    protected final String eventDomainObjectName;

    public ContextualEventBuilder(ImmutableComponentAddress path,
            String eventDomainObjectName) {
        super(path);
        this.eventDomainObjectName = eventDomainObjectName;
    }

    @Override
    public EventModel<T> build(DomainModel model) {

    }

    @Override
    protected ContextualEventBuilder<T, E> self() {
        return this;
    }

    @Override
    public Set<String> getDependencies() {
    }

}
