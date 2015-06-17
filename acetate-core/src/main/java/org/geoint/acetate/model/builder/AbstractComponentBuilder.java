package org.geoint.acetate.model.builder;

import java.util.HashSet;
import java.util.Set;
import org.geoint.acetate.impl.model.ImmutableObjectAddress;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.attribute.ModelAttribute;
import org.geoint.acetate.model.constraint.DataConstraint;

/**
 *
 * @param <T>
 * @param <B>
 */
public abstract class AbstractComponentBuilder<T, B extends AbstractComponentBuilder>
        implements DomainObjectDependentBuilder {

    protected final ImmutableObjectAddress path;
    protected String description;
    protected Set<ModelAttribute> attributes = new HashSet<>();
    protected Set<DataConstraint> constraints = new HashSet<>();

    public AbstractComponentBuilder(ImmutableObjectAddress path) {
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
    public B attribute(ModelAttribute attribute) {
        this.attributes.add(attribute);
        return self();
    }

    /**
     * Add a constraint to the base domain model object.
     *
     * @param constraint
     * @return this builder (fluid interface)
     */
    public B constraint(DataConstraint constraint) {
        this.constraints.add(constraint);
        return self();
    }

    protected ImmutableObjectAddress path() {
        return path;
    }

    abstract protected B self();

    abstract public ModelComponent<T> build(DomainModel model)
            throws ComponentCollisionException, IncompleteModelException;
}
