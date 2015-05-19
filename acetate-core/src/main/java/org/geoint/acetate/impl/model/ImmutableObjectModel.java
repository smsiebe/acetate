package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.geoint.acetate.data.transform.ComplexObjectCodec;
import org.geoint.acetate.model.ModelContextPath;
import org.geoint.acetate.model.ContextualComponent;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.DomainObjectOperation;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 *
 * @param <T>
 */
public class ImmutableObjectModel<T> implements DomainObject<T> {

    private final ImmutableContextPath path;
    private final String name;
    private final Optional<String> description;
    private final ComplexObjectCodec<T> codec;
    private final Collection<? extends DomainObjectOperation> operations;
    private final Collection<ContextualComponent> composites;
    private final Collection<? extends ComponentConstraint> constraints;
    private final Collection<? extends ComponentAttribute> attributes;

    protected ImmutableObjectModel(ImmutableContextPath path,
            String name,
            String description,
            ComplexObjectCodec<T> codec,
            Collection<ImmutableOperationModel> operations,
            Collection<ContextualComponent> composites,
            Collection<? extends ComponentConstraint> constraints,
            Collection<? extends ComponentAttribute> attributes) {
        this.path = path;
        this.name = name;
        this.description = Optional.ofNullable(description);
        this.operations = Collections.unmodifiableCollection(operations);
        this.composites = Collections.unmodifiableCollection(composites);
        this.codec = codec;
        this.constraints = Collections.unmodifiableCollection(constraints);
        this.attributes = Collections.unmodifiableCollection(attributes);
    }

    /**
     * Create a "base" domain model object.
     *
     * @param <T> data type
     * @param domainName
     * @param version
     * @param name
     * @param description optional (nullable) model description
     * @param codec
     * @param operations
     * @param composites
     * @param constraints
     * @param attributes
     * @return domain model "base" object
     */
    public static <T> ImmutableObjectModel<T> base(
            String domainName,
            long version,
            String name,
            String description,
            ComplexObjectCodec<T> codec,
            Collection<ImmutableOperationModel> operations,
            Collection<ContextualComponent> composites,
            Collection<? extends ComponentConstraint> constraints,
            Collection<? extends ComponentAttribute> attributes) {
        return new ImmutableObjectModel(
                ImmutableContextPath.basePath(domainName, version, name),
                name, description, codec, operations, composites, constraints,
                attributes);
    }

    /**
     * Create a "base" domain model object.
     *
     * @param <T> data type
     * @param path
     * @param name
     * @param description optional (nullable) model description
     * @param codec
     * @param operations
     * @param composites
     * @param constraints
     * @param attributes
     * @return domain model "base" object
     */
    public static <T> ImmutableObjectModel<T> base(
            ImmutableContextPath path,
            String name,
            String description,
            ComplexObjectCodec<T> codec,
            Collection<ImmutableOperationModel> operations,
            Collection<ContextualComponent> composites,
            Collection<? extends ComponentConstraint> constraints,
            Collection<? extends ComponentAttribute> attributes) {
        return new ImmutableObjectModel<>(path, name, description, codec,
                operations, composites, constraints, attributes
        );
    }

    @Override
    public ModelContextPath getPath() {
        return path;
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
    public Collection<DomainObjectOperation> getOperations() {
        return (Collection<DomainObjectOperation>) operations;
    }

    @Override
    public Collection<ContextualComponent> getAggregates() {
        return composites;
    }

    @Override
    public Collection<ComponentAttribute> getAttributes() {
        return (Collection<ComponentAttribute>) attributes;
    }

    @Override
    public Collection<ComponentConstraint> getConstraints() {
        return (Collection<ComponentConstraint>) constraints;
    }

    @Override
    public ComplexObjectCodec<T> getCodec() {
        return codec;
    }

    @Override
    public String toString() {
        return path.asString();
    }
}
