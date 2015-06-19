
package org.geoint.acetate.impl.model;

import java.util.Collection;
import org.geoint.acetate.model.ComposedModel;
import org.geoint.acetate.model.ContextualModel;
import org.geoint.acetate.model.DomainId;
import org.geoint.acetate.model.ModelVisitor;
import org.geoint.acetate.model.attribute.ModelAttribute;
import org.geoint.acetate.model.constraint.DataConstraint;

/**
 *
 * @param <T>
 */
public class ImmutableComposedModel<T> extends ImmutableDataModel<T>{

    private final Collection<ComposedModel<? super T>> parents;
    private final Collection<ComposedModel<? extends T>> specialized;
    private final Collection<ContextualModel> composites;

    public ImmutableComposedModel(Collection<ComposedModel<? super T>> parents, Collection<ComposedModel<? extends T>> specialized, Collection<ContextualModel> composites, DomainId domainId, Class<T> dataType, String name, String description, Collection<ModelAttribute> attributes, Collection<DataConstraint> constraints) {
        super(domainId, dataType, name, description, attributes, constraints);
        this.parents = parents;
        this.specialized = specialized;
        this.composites = composites;
    }
    
    
    @Override
    public void visit(ModelVisitor visitor) {
        
    }

}
