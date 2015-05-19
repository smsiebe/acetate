package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.model.DomainAggregateObject;
import org.geoint.acetate.model.DomainCompositeObject;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelContextPath;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.DomainOperation;
import org.geoint.acetate.model.Inheritable;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Domain model object instance.
 *
 * @param <T>
 */
public class DomainObjectImpl<T> implements DomainObject<T> {

    private final DomainModel model;
    private final ImmutableContextPath path;
    private final String domainObjectName;
    private final Set<String> parentComponents;
    private final Optional<String> description;
    private final Collection<DomainOperation<?>> operations;
    private final Collection<DomainCompositeObject<?>> composites;
    private final Collection<DomainAggregateObject<?>> aggregates;
    private final Collection<ComponentConstraint> constraints;
    private final Collection<ComponentAttribute> attributes;
    private final BinaryCodec<T> binaryCodec;
    private final CharacterCodec<T> charCodec;

    private DomainObjectImpl(DomainModel model,
            ImmutableContextPath path,
            String name,
            String description,
            Collection<String> parentObjects,
            Collection<DomainOperation<?>> operations,
            Collection<DomainCompositeObject<?>> composites,
            Collection<DomainAggregateObject<?>> aggregates,
            Collection<ComponentConstraint> constraints,
            Collection<ComponentAttribute> attributes,
            BinaryCodec<T> binaryCodec,
            CharacterCodec<T> charCodec) {
        this.model = model;
        this.path = path;
        this.domainObjectName = name;
        this.parentComponents = Collections.unmodifiableSet(new HashSet<>(parentObjects));
        this.description = Optional.ofNullable(description);
        this.operations = inherit(model, this.parentComponents, DomainObject::getOperations, operations);
        this.composites = Collections.unmodifiableCollection(composites);
        this.aggregates = Collections.unmodifiableCollection(aggregates);
        this.constraints = Collections.unmodifiableCollection(constraints);
        this.attributes = Collections.unmodifiableCollection(attributes);
        this.binaryCodec = binaryCodec;
        this.charCodec = charCodec;
    }

    private <I extends Inheritable> Collection<I> inherit(DomainModel model,
            Set<String> parents,
            Function<? super DomainObject<?>, Collection<I>> inherited,
            Collection<I> local) {
        List<I> combinedItems = parents.stream()
                .map((pn) -> model.getComponents().findByName(pn))
                .filter((p) -> p.isPresent())
                .map((p) -> p.get())
                .flatMap((p) -> inherited.apply(p).stream())
                .filter((i) -> i.inherit())
                .collect(Collectors.toList());
        //add local
        combinedItems.addAll(local);
        return Collections.unmodifiableCollection(combinedItems);
    }

    @Override
    public ModelContextPath getPath() {
        return path;
    }

    @Override
    public String getName() {
        return domainObjectName;
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public Collection<DomainOperation<?>> getOperations() {
        return operations;
    }

    @Override
    public Collection<DomainAggregateObject<?>> getAggregates() {
        return aggregates;
    }

    @Override
    public Collection<DomainCompositeObject<?>> getComposites() {
        return composites;
    }

    @Override
    public Collection<ComponentAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<ComponentConstraint> getConstraints() {
        return constraints;
    }

    @Override
    public Collection<DomainOperation<?>> getNativeOperations() {
        return operations.stream()
                .filter((o) -> o instanceof InheritedDomainOperation)
                .collect(Collectors.toList());
    }

    @Override
    public CharacterCodec<T> getCharacterCodec() {
        return charCodec;
    }

    @Override
    public BinaryCodec<T> getBinaryCodec() {
        return binaryCodec;
    }

    @Override
    public DomainModel getDomainModel() {
        return model;
    }

    @Override
    public Collection<DomainObject<? super T>> inheritsFrom() {
        return parentComponents.stream()
                .map((pn) -> model.getComponents().findByName(pn))
                .filter((optional) -> optional.isPresent())
                .map((optional) -> (DomainObject<? super T>) optional.get())
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return path.asString();
    }

}
