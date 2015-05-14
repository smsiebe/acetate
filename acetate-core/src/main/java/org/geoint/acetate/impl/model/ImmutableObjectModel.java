package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.model.ComponentPath;
import org.geoint.acetate.model.ContextualComponent;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.OperationModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 *
 * @param <T>
 */
public class ImmutableObjectModel<T> implements ObjectModel<T> {

    private final ImmutableComponentPath path;
    private final String name;
    private final Optional<String> description;
    private final ObjectCodec<T> codec;
    private final Collection<? extends OperationModel> operations;
    private final Collection<ContextualComponent> composites;
    private final Collection<? extends ComponentConstraint> constraints;
    private final Collection<? extends ComponentAttribute> attributes;

    ImmutableObjectModel(ImmutableComponentPath path,
            String name,
            Optional<String> description,
            ObjectCodec<T> codec,
            Collection<ImmutableOperationModel> operations,
            Collection<ContextualComponent> composites,
            Collection<? extends ComponentConstraint> constraints,
            Collection<? extends ComponentAttribute> attributes) {
        this.path = path;
        this.name = name;
        this.description = description;
        this.operations = operations;
        this.composites = composites;
        this.codec = codec;
        this.constraints = constraints;
        this.attributes = attributes;
    }

    /**
     * Create a "base" domain model object.
     *
     * @param <T> data type
     * @param domainName
     * @param version
     * @param name
     * @param description
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
            ObjectCodec<T> codec,
            Collection<ImmutableOperationModel> operations,
            Collection<ContextualComponent> composites,
            Collection<? extends ComponentConstraint> constraints,
            Collection<? extends ComponentAttribute> attributes) {
        final ImmutableComponentPath path
                = ImmutableComponentPath.basePath(domainName, version, name);
        return new ImmutableObjectModel(path,
                name,
                Optional.ofNullable(description),
                codec,
                Collections.unmodifiableCollection(operations),
                Collections.unmodifiableCollection(composites),
                Collections.unmodifiableCollection(constraints),
                Collections.unmodifiableCollection(attributes));
    }
//
//    public ImmutableObjectModel(String componentName,
//            ImmutableComponentContext context,
//            Collection<ImmutableObjectModel<? super T>> inheritedFrom,
//            Collection<OperationModel> operations,
//            ComponentRegistry registry) {
//        this(componentName, inheritedFrom, context, operations);
//    }
//
//    public ImmutableObjectModel(String name,
//            Collection<ImmutableObjectModel<? super T>> inheritedFrom,
//            ImmutableComponentContext context,
//            Collection<OperationModel> operations) {
//        this.name = name;
//        this.context = context;
//        this.lineage = Collections.unmodifiableSet(new HashSet<>(inheritedFrom));
//
//        //component operations are native + inherited
//        Map<String, OperationModel> ops = new HashMap<>();
//        operations.stream()
//                .forEach((o) -> ops.put(o.getName(), o));
//        this.lineage.stream()
//                .flatMap((p) -> p.getOperations().stream())
//                .filter(OperationModel::inherit)
//                .filter((o) -> !ops.containsKey(o.getName()))
//                .forEach((o) -> ops.put(o.getName(), o));
//        this.operations = Collections.unmodifiableCollection(ops.values());
//    }

    @Override
    public ComponentPath getPath() {
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
    public Collection<OperationModel> getOperations() {
        return (Collection<OperationModel>) operations;
    }

    @Override
    public Collection<? extends ContextualComponent> getComposites() {
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
    public ObjectCodec<T> getCodec() {
        return codec;
    }

    @Override
    public String toString() {
        return path.asString();
    }
}
