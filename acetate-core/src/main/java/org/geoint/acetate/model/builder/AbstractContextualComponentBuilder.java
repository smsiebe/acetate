package org.geoint.acetate.model.builder;

import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableObjectPath;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Base domain model object builder used override default "base model" traits.
 *
 * @param <T>
 * @param <B>
 */
public abstract class AbstractContextualComponentBuilder<T, B extends AbstractContextualComponentBuilder>
        extends AbstractObjectBuilder<T, B> {

    protected final String baseComponentName;
    protected final boolean isCollection;
    protected boolean inheritAttributes;
    protected boolean inheritConstraints;

    public AbstractContextualComponentBuilder(ImmutableObjectPath path,
            String baseComponentName, boolean isCollection) {
        super(path);
        this.baseComponentName = baseComponentName;
        this.isCollection = isCollection;
    }

    /**
     * Override the base model description in this context.
     *
     * @param description description of the object model
     * @return this builder (fluid interface)
     */
    @Override
    public B description(String description) {
        super.description(description);
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
    @Override
    public B attribute(ComponentAttribute attribute) {
        super.attribute(attribute);
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
    @Override
    public B constraint(ComponentConstraint constraint) {
        super.constraint(constraint);
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

}
