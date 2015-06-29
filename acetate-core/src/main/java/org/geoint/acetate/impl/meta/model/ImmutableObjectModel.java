package org.geoint.acetate.impl.meta.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.geoint.acetate.meta.MetaVersion;
import org.geoint.acetate.meta.model.ObjectModel;
import org.geoint.acetate.meta.model.OperationModel;

/**
 *
 * @param <T>
 */
class ImmutableObjectModel implements ObjectModel {

    private final DomainId domainId;
    private final Map<String, String> attributes;
    private final Map<String, OperationModel> declaredOperations;
    private Map<String, OperationModel> allOperations;
    private final Collection<ObjectModel> parents;
    private final Collection<ObjectModel> specialized;

    public ImmutableObjectModel(String domainName,
            MetaVersion domainVersion,
            String objectName,
            Map<String, String> attributes,
            Collection<ImmutableOperationModel> declaredOperations,
            Collection<ImmutableObjectModel> parents,
            Collection<ImmutableObjectModel> specialized) {
        this.domainId = DomainId.getInstance(domainName, domainVersion, objectName);
        this.attributes = Collections.unmodifiableMap(attributes);
        this.declaredOperations = Collections.unmodifiableMap(
                declaredOperations.stream().collect(
                        Collectors.toMap((o) -> o.getOperationName(), (o) -> o))
        );

        this.parents = Collections.unmodifiableCollection(parents);
        this.specialized = Collections.unmodifiableCollection(specialized);
    }

    @Override
    public String getName() {
        return this.domainId.getObjectName();
    }

    @Override
    public String getDomainName() {
        return this.domainId.getDomainName();
    }

    @Override
    public MetaVersion getDomainVersion() {
        return this.domainId.getDomainVersion();
    }

    @Override
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public Optional<String> getAttribute(String attributeName) {
        return Optional.ofNullable(attributes.get(attributeName));
    }

    @Override
    public Collection<OperationModel> getDeclaredOperations() {
        return declaredOperations.values();
    }

    @Override
    public synchronized Collection<OperationModel> getOperations() {

        if (allOperations == null) {
            //if all operations have not been cached yet, create cache
            this.allOperations = Collections.unmodifiableMap(
                    Stream.concat(declaredOperations.values().stream(),
                            parents.stream().flatMap(
                                    (p) -> p.getOperations().stream()
                            )
                            //if the subclass 'overrides' this operation, ignore it
                            .filter((o) -> !this.declaredOperations.containsKey(o.getOperationName()))
                    )
                    .collect(Collectors.toMap((o) -> o.getOperationName(), (o) -> o)));
        }
        return allOperations.values();
    }

    @Override
    public Collection<ObjectModel> getParents() {
        return parents;
    }

    @Override
    public Collection<ObjectModel> getSpecialized() {
        return specialized;
    }

}
