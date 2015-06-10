package org.geoint.acetate.model.builder;

import java.util.HashSet;
import java.util.Set;
import org.geoint.acetate.impl.model.ImmutableComponentAddress;
import org.geoint.acetate.model.DomainComponent;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Base domain model object builder used override default "base model" traits.
 *
 * @param <T>
 * @param <B>
 */
public abstract class AbstractContextualComponentBuilder<T, B extends AbstractContextualComponentBuilder>
        implements DomainObjectDependentBuilder {

    protected final ImmutableComponentAddress path;
    protected String description;
    protected Set<ComponentAttribute> attributes = new HashSet<>();
    protected Set<ComponentConstraint> constraints = new HashSet<>();
    protected boolean inheritAttributes;
    protected boolean inheritConstraints;

    public AbstractContextualComponentBuilder(ImmutableComponentAddress path) {
        this.path = path;
    }

    /**
     * Override the base model description in this context.
     *
     * @param description description of the object model
     * @return this builder (fluid interface)
     */
    public B description(String description) {
        this.description = description;
        return self();
    }

    /**
     * Adds an attribute for this object in this context.
     *
     * Adding a single attribute remove all "base" attributes. If they are
     * desired, you must call {@link #inheritAttributes}.
     *
     * @param attribute
     * @return this builder (fluid interface)
     */
    public B attribute(ComponentAttribute attribute) {
        this.attributes.add(attribute);
        return self();
    }

    /**
     * By default the contextual object inherits all attributes from the base
     * model.
     *
     * When {@link #attribute(ComponentAttribute) } is called, all base
     * attributes are automatically removed from this context. This method may
     * be called to explicitly ensure all attributes are inherited (or not).
     *
     * @param inherit true to inherit all attributes from base object
     * @return this builder (fluid interface)
     *
     */
    public B inheritAttributes(boolean inherit) {
        this.inheritAttributes = inherit;
        return self();
    }

    /**
     * Add a constraint for this object in this context.
     *
     * Adding a single constraint removes all "base" constraints. If they are
     * desired, you must call {#link inheritConstraints}.
     *
     * @param constraint
     * @return this builder (fluid interface)
     */
    public B constraint(ComponentConstraint constraint) {
        this.constraints.add(constraint);
        return self();
    }

    /**
     * By default the contextual object inherits all constraints from the base
     * model.
     *
     * When {@link #constraint(ComponentConstraint) } is called, all base
     * constraints are automatically removed from this context. This method may
     * be called to explicitly ensure all constraints are inherited (or not).
     *
     * @param inherit true to inherit all constraints from base object
     * @return this builder (fluid interface)
     */
    public B inheritConstraints(boolean inherit) {
        this.inheritConstraints = inherit;
        return self();
    }

    protected ImmutableComponentAddress path() {
        return path;
    }

    abstract protected B self();

    abstract public DomainComponent<T> build(DomainModel model)
            throws ComponentCollisionException, IncompleteModelException;
}
