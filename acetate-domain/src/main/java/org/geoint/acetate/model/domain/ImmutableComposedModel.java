package org.geoint.acetate.model.domain;

import java.util.Collection;
import org.geoint.acetate.model.ComposedModel;
import org.geoint.acetate.model.ContextualModel;
import org.geoint.acetate.impl.meta.model.DomainId;
import org.geoint.acetate.model.ModelVisitor;
import org.geoint.acetate.model.attribute.ModelAttribute;
import org.geoint.acetate.model.constraint.DataConstraint;

/**
 *
 * @param <T>
 */
public class ImmutableComposedModel<T> extends ImmutableDataModel<T> {

    private final Collection<ComposedModel<? super T>> parents;
    private final Collection<ComposedModel<? extends T>> specialized;
    private final Collection<ContextualModel> composites;

    public ImmutableComposedModel(
            DomainId domainId,
            Class<T> dataType,
            String name,
            String description,
            Collection<ComposedModel<? super T>> parents,
            Collection<ComposedModel<? extends T>> specialized,
            Collection<ContextualModel> composites,
            Collection<ModelAttribute> attributes,
            Collection<DataConstraint> constraints) {
        super(domainId, dataType, name, description, attributes, constraints);
        this.parents = parents;
        this.specialized = specialized;
        this.composites = composites;
    }

    public Collection<ComposedModel<? super T>> getParents() {
        return parents;
    }

    public Collection<ComposedModel<? extends T>> getSpecialized() {
        return specialized;
    }

    public Collection<ContextualModel> getComposites() {
        return composites;
    }

    @Override
    public void visit(ModelVisitor visitor) {

    }

}
