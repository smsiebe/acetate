package org.geoint.acetate.impl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.geoint.acetate.data.transform.ComplexObjectCodec;
import org.geoint.acetate.model.ContextualComponent;
import org.geoint.acetate.model.Inheritable;
import org.geoint.acetate.model.InheritedObjectModel;
import org.geoint.acetate.model.DomainComponent;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.DomainObjectOperation;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 *
 * @param <T>
 */
public class ImmutableInheritedObjectModel<T> extends ImmutableObjectModel<T>
        implements InheritedObjectModel<T> {

    private final Collection<DomainObject<? super T>> lineage;

    private ImmutableInheritedObjectModel(ImmutableContextPath path,
            String name,
            String description,
            ComplexObjectCodec<T> codec,
            Collection<DomainObject<? super T>> lineage,
            Collection<ImmutableOperationModel> combinedOperations,
            Collection<ContextualComponent> combinedComposites,
            Collection<? extends ComponentConstraint> constraints,
            Collection<? extends ComponentAttribute> attributes) {
        super(path,
                name,
                description,
                codec,
                combinedOperations,
                combinedComposites,
                constraints,
                attributes);
        this.lineage = lineage;
    }

    /**
     * Create a "base" domain model object which inherits from other "based"
     * domain model objects.
     *
     * @param <T>
     * @param domainName
     * @param version
     * @param name
     * @param description optional (nullable) object model description
     * @param codec
     * @param lineage models from which this model inherits
     * @param localOperations
     * @param localComposites
     * @param constraints
     * @param attributes
     * @return
     */
    public static <T> ImmutableInheritedObjectModel<T> base(
            String domainName,
            long version,
            String name,
            String description,
            ComplexObjectCodec<T> codec,
            Collection<ImmutableObjectModel<?>> lineage,
            Collection<DomainObjectOperation> localOperations,
            Collection<ContextualComponent> localComposites,
            Collection<? extends ComponentConstraint> constraints,
            Collection<? extends ComponentAttribute> attributes) {
        final ImmutableContextPath path
                = ImmutableContextPath.basePath(domainName, version, name);

        //TODO consider creating specialized Inherited* types instead of passing
        //     a collection of inherited traits
        Collection<DomainObjectOperation> combinedOperations
                = inherit(lineage, localOperations,
                        (l) -> l.getOperations().stream(),
                        DomainComponent::getName);

        Collection<ContextualComponent> combinedComposites
                = inherit(lineage, localComposites,
                        (l) -> l.getAggregates().stream(),
                        DomainComponent::getName);

        return new ImmutableInheritedObjectModel(path, name, description,
                codec, lineage, combinedOperations, combinedComposites,
                constraints, attributes);
    }

    private static <I extends Inheritable> Collection<I> inherit(
            Collection<ImmutableObjectModel<?>> lineage,
            Collection<I> nativeComponents,
            Function<ImmutableObjectModel<?>, Stream<I>> flattener,
            Function<I, String> namer) {
        Map<String, I> combined
                = lineage.stream().flatMap((l) -> flattener.apply(l))
                .filter((o) -> o.inherit())
                .collect(Collectors.toMap(
                                (i) -> namer.apply(i),
                                (i) -> i)
                );
        nativeComponents.stream()
                .forEach((l) -> combined.put(
                                namer.apply(l),
                                l)
                );
        return combined.values();
    }

    @Override
    public Collection<DomainObject<? super T>> inheritsFrom() {
        return lineage;
    }

}
