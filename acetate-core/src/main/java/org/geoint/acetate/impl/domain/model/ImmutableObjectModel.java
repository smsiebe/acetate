package org.geoint.acetate.impl.domain.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.entity.model.OperationModel;
import org.geoint.acetate.model.ModelVersion;

/**
 * Domain model object.
 */
class ImmutableObjectModel implements ObjectModel {

    private final ObjectId objectId;
    private final Map<String, String> attributes;
    private final Map<String, OperationModel> declaredOperations;
    private Map<String, OperationModel> allOperations;
    private final Collection<ObjectModel> parents;
    private final Collection<ObjectModel> specialized;

    public ImmutableObjectModel(ObjectId objectId,
            Map<String, String> attributes,
            Collection<OperationModel> declaredOperations,
            Collection<ObjectModel> parents,
            Collection<ObjectModel> specialized) {
        this.objectId = objectId;
        this.attributes = Collections.unmodifiableMap(attributes);
        this.declaredOperations = Collections.unmodifiableMap(
                declaredOperations.stream().collect(
                        Collectors.toMap((o) -> o.getName(), (o) -> o))
        );

        this.parents = Collections.unmodifiableCollection(parents);
        this.specialized = Collections.unmodifiableCollection(specialized);
    }

    @Override
    public String getName() {
        return this.objectId.getObjectName();
    }

    @Override
    public String getDomainName() {
        return this.objectId.getDomainName();
    }

    @Override
    public ModelVersion getDomainVersion() {
        return this.objectId.getDomainVersion();
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
                            .filter((o) -> !this.declaredOperations.containsKey(o.getName()))
                    )
                    .collect(Collectors.toMap((o) -> o.getName(), (o) -> o)));
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

    @Override
    public String toString() {
        return this.objectId.asString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.objectId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImmutableObjectModel other = (ImmutableObjectModel) obj;
        if (!Objects.equals(this.objectId, other.objectId)) {
            return false;
        }
        return true;
    }

}
