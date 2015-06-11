package org.geoint.acetate.model.builder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.geoint.acetate.impl.model.ImmutableObjectModel;
import org.geoint.acetate.impl.model.ImmutableObjectAddress.ImmutableComponentAddress;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ObjectModel;

/**
 * API to programmatically build an {@link ObjectModel}.
 *
 * This builder is <b>NOT</b> thread safe.
 *
 * @param <T> component data type
 */
public class DomainObjectBuilder<T>
        extends AbstractObjectBuilder<T, DomainObjectBuilder<T>> {

    protected final Set<String> parentObjects = new HashSet<>();

    //Entity Object callout/flags
    private String entityGuidComponent;
    private String entityVersionComponent;

    public DomainObjectBuilder(ImmutableComponentAddress path) {
        super(path);
    }

    @Override
    public ObjectModel<T> build(DomainModel model)
            throws ComponentCollisionException, IncompleteModelException {

        final String objectName = this.path().getComponentName();
        if (model.getComponents().findByName(objectName).isPresent()) {
            //registry already has a component by this name, don't even 
            //bother building
            throw new ComponentCollisionException(path);
        }

        if (entityGuidComponent != null) {
            if (entityVersionComponent == null) {
                throw new IncompleteModelException(path.getDomainName(),
                        path.getDomainVersion(),
                        "Domain Entity objects require both an GUID and version "
                        + "component.");
            }

            return new ImmutableObjectModel(model, path,
                    this.path.getComponentName(),
                    description,
                    parentObjects,
                    operations.values().stream().map((b) -> b.build(model))
                    .collect(Collectors.toList()),
                    composites.values().stream().map((b) -> b.build(model))
                    .collect(Collectors.toList()),
                    aggregates.values().stream().map((b) -> b.build(model))
                    .collect(Collectors.toList()),
                    constraints,
                    attributes,
                    binaryCodec,
                    charCodec);
        }

    }

    /**
     * Indicates that the component inherits from the provided component name.
     *
     * @param parentObjectName domain-unique name of object from which this
     * component inherits
     * @return this builder (fluid interface)
     */
    public DomainObjectBuilder<T> specializes(String parentObjectName) {
        parentObjects.add(parentObjectName);
        return this;
    }

    @Override
    protected DomainObjectBuilder<T> self() {
        return this;
    }

    @Override
    public Set<String> getDependencies() {
        Set<String> d = new HashSet<>();
        d.addAll(this.parentObjects);
        addAllDependencies(d, aggregates);
        addAllDependencies(d, composites);
        addAllDependencies(d, operations);
        return d;
    }

    private void addAllDependencies(Set<String> deps,
            Map<String, ? extends DomainObjectDependentBuilder> depMap) {
        depMap.entrySet().stream()
                .map((e) -> e.getValue())
                .forEach((a) -> deps.addAll(a.getDependencies()));
    }
}
