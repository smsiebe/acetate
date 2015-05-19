package org.geoint.acetate.model.builder;

import java.util.HashSet;
import java.util.Set;
import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableObjectPath;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainObject;

/**
 * API to programmatically build an {@link DomainObject}.
 *
 * This builder is <b>NOT</b> thread safe.
 *
 * @param <T> component data type
 */
public class DomainObjectBuilder<T>
        extends AbstractObjectBuilder<T, DomainObjectBuilder<T>> {

    protected final Set<String> parentObjects = new HashSet<>();

    public DomainObjectBuilder(ImmutableObjectPath path) {
        super(path);
    }

    public DomainObject<T> build(DomainModel model) {

        final String componentName = cb.componentName;
        final ImmutableContextPath objectPath
                = ImmutableContextPath.basePath(name, version, componentName);

        if (registry.findByName(componentName).isPresent()) {
            //registry already has a component by this name, don't even 
            //bother building
            throw new ComponentCollisionException(objectPath);
        }

        if (cb.inheritedComponents.isEmpty()) {
            //non-inherited object model
            return DomainObjectImpl.base(
                    objectPath,
                    componentName,
                    cb.description,
                    cb.codec,
                    cb.operations.values(),
                    cb.compositeComponents,
                    cb.constraints,
                    cb.attributes);
        } else {
            //inherited object model
            return ImmutableInheritedObjectModel.base(
                    objectPath,
                    componentName,
                    cb.description,
                    cb.codec,
                    cb.operations.values(),
                    cb.compositeComponents,
                    cb.constraints,
                    cb.attributes);
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
        
    }

}
