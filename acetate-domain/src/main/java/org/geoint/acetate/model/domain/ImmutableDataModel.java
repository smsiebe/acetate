package org.geoint.acetate.model.domain;

import java.util.Collection;
import java.util.Collections;
import org.geoint.acetate.impl.model.ImmutableModelComponent;
import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.model.ModelVisitor;
import org.geoint.acetate.model.DomainId;
import org.geoint.acetate.model.ModelVisitorResult;
import org.geoint.acetate.model.attribute.ModelAttribute;
import org.geoint.acetate.model.constraint.DataConstraint;

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
