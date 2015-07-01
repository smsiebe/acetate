package org.geoint.acetate.entity.impl.model;

import java.util.Collection;
import java.util.Collections;
import org.geoint.acetate.impl.model.ImmutableModelComponent;
import org.geoint.acetate.entity.model.DataModel;
import org.geoint.acetate.entity.model.ModelVisitor;
import org.geoint.acetate.impl.meta.model.DomainId;
import org.geoint.acetate.entity.model.ModelVisitorResult;
import org.geoint.acetate.entity.attic.attribute.ModelAttribute;
import org.geoint.acetate.entity.attic.constraint.DataConstraint;

/**
 *
 * @param <T>
 */
public abstract class ImmutableDataModel<T> extends ImmutableModelComponent
        implements DataModel<T> {

    private final Class<T> dataType;
    private final Collection<DataConstraint> constraints;

    public ImmutableDataModel(DomainId domainId, Class<T> dataType,
            String name, String description,
            Collection<ModelAttribute> attributes,
            Collection<DataConstraint> constraints) {
        super(domainId, name, description, attributes);
        this.dataType = dataType;
        this.constraints = Collections.unmodifiableCollection(constraints);
    }

    @Override
    public Class<T> getDataClass() {
        return dataType;
    }

    @Override
    public Collection<DataConstraint> getConstraints() {
        return constraints;
    }
    
}
