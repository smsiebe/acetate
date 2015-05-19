package org.geoint.acetate.model.builder;

import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableObjectPath;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainObject;

/**
 * Programmatic API to define domain object aggregate relationship.
 *
 * @param <T>
 */
public class DomainAggregateObjectBuilder<T>
        extends AbstractContextualObjectBuilder<T, DomainAggregateObjectBuilder<T>> {

    private String relOptComponent;
    private String relStateComponent;

    public DomainAggregateObjectBuilder(ImmutableObjectPath path,
            String baseComponentName, boolean isCollection) {
        super(path, baseComponentName, isCollection);
    }

    @Override
    public DomainObject<T> build(DomainModel model) {
        
    }

    public DomainAggregateObjectBuilder<T> relationshipOptionsComponent(String localName) {
        this.relOptComponent = localName;
        return this;
    }

    public DomainAggregateObjectBuilder<T> relationshipStateComponent(String localName) {
        this.relStateComponent = localName;
        return this;
    }

    @Override
    protected DomainAggregateObjectBuilder<T> self() {
        return this;
    }

}
