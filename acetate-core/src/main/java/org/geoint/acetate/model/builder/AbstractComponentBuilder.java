package org.geoint.acetate.model.builder;

import java.util.HashSet;
import java.util.Set;
import org.geoint.acetate.impl.model.ImmutableContextPath;
import org.geoint.acetate.model.DomainComponent;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.annotation.Domain;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 *
 * @param <T>
 * @param <B>
 */
public abstract class AbstractComponentBuilder<T, B extends AbstractComponentBuilder>
        implements DomainObjectDependentBuilder {

    protected final ImmutableContextPath path;
    protected String description;
    protected Set<ComponentAttribute> attributes = new HashSet<>();
    protected Set<ComponentConstraint> constraints = new HashSet<>();

    public AbstractComponentBuilder(ImmutableContextPath path) {
        this.path = path;
    }

    /**
     * Sets the (optional) object model description.
     *
     * @param description description of the object model
     * @return this builder (fluid interface)
     */
    public B description(String description) {
        this.description = description;
        return self();
    }

    /**
     * Adds an attribute to the base domain model object.
     *
     * @param attribute
     * @return this builder (fluid interface)
     */
    public B attribute(ComponentAttribute attribute) {
        this.attributes.add(attribute);
        return self();
    }

    /**
     * Add a constraint to the base domain model object.
     *
     * @param constraint
     * @return this builder (fluid interface)
     */
    public B constraint(ComponentConstraint constraint) {
        this.constraints.add(constraint);
        return self();
    }

    protected ImmutableContextPath path() {
        return path;
    }

    abstract protected B self();

    abstract public DomainComponent<T> build(DomainModel model);
}
