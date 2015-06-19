package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.geoint.acetate.model.DomainId;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.ModelVisitor;
import org.geoint.acetate.model.attribute.ModelAttribute;

/**
 *
 */
public abstract class ImmutableModelComponent implements ModelComponent {

    private final DomainId domainId;
    private final String name;
    private final Optional<String> description;
    private final Collection<ModelAttribute> attributes;

    public ImmutableModelComponent(DomainId domainId, String name,
            String description, Collection<ModelAttribute> attributes) {
        this.domainId = domainId;
        this.name = name;
        this.description = Optional.ofNullable(description);
        //populate attribute type index and attributes collection
        this.attributes = Collections.unmodifiableCollection(attributes);
    }

    @Override
    public DomainId getDomainId() {
        return domainId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public Collection<? extends ModelAttribute> getAttributes() {
        return attributes;
    }

}
