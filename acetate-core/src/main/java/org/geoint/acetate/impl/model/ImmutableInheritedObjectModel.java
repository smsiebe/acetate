package org.geoint.acetate.impl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.model.ContextualComponent;
import org.geoint.acetate.model.Inheritable;
import org.geoint.acetate.model.InheritedObjectModel;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.OperationModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 *
 * @param <T>
 */
public class ImmutableInheritedObjectModel<T> extends ImmutableObjectModel<T>
        implements InheritedObjectModel<T> {

    private final Collection<ObjectModel<? super T>> lineage;
    private final Collection<Inheritable> inherited;

    private ImmutableInheritedObjectModel(ImmutableComponentPath path,
            String name,
            Optional<String> description,
            ObjectCodec<T> codec,
            Collection<ObjectModel<? super T>> lineage,
            Collection<ImmutableOperationModel> combinedOperations,
            Collection<ContextualComponent> combinedComposites,
            Collection<? extends ComponentConstraint> constraints,
            Collection<? extends ComponentAttribute> attributes,
            Collection<Inheritable> inherited) {
        super(path,
                name,
                description,
                codec,
                combinedOperations,
                combinedComposites,
                constraints,
                attributes);
        this.lineage = lineage;
        this.inherited = inherited;
    }

    public static <T> ImmutableInheritedObjectModel<T> inherited(
            String domainName,
            long version,
            String name,
            String description,
            ObjectCodec<T> codec,
            Collection<ImmutableObjectModel<?>> lineage,
            Collection<ImmutableOperationModel> localOperations,
            Collection<ContextualComponent> localComposites,
            Collection<? extends ComponentConstraint> constraints,
            Collection<? extends ComponentAttribute> attributes) {
        final ImmutableComponentPath path
                = ImmutableComponentPath.basePath(domainName, version, name);

        //TODO move common algorithm to method and pass functions 
        //TODO consider creating specialized Inerited* types instead of passing
        //     a collection of inherited traits
        Collection<Inheritable> inherited = new ArrayList<>();
        Map<String, OperationModel> combinedOperations
                = lineage.stream().flatMap((l) -> l.getOperations().stream())
                .filter((o) -> o.inherit())
                .collect(Collectors.toMap(
                                (i) -> i.getName(),
                                (i) -> i)
                );
        inherited.addAll(combinedOperations.values());
        localOperations.stream()
                .forEach((o) -> combinedOperations.put(o.getName(), o));

        Map<String, ContextualComponent> combinedComposites
                = lineage.stream().flatMap((l) -> l.getComposites().stream())
                .filter((o) -> o.inherit())
                .collect(Collectors.toMap(
                                (i) -> i.getName(),
                                (i) -> i)
                );
        inherited.addAll(combinedComposites.values());
        localComposites.stream()
                .forEach((c) -> combinedComposites.put(c.getName(), c));

        return new ImmutableInheritedObjectModel(path,
                name,
                Optional.ofNullable(description),
                codec,
                lineage,
                combinedOperations.values(),
                combinedComposites.values(),
                constraints,
                attributes,
                inherited);
    }

    @Override
    public Collection<ObjectModel<? super T>> inheritsFrom() {
        return lineage;
    }

    @Override
    public Collection<Inheritable> getInheritedTraits() {
        return inherited;
    }

}
