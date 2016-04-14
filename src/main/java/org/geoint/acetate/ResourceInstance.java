/*
 * Copyright 2016 geoint.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geoint.acetate;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.geoint.acetate.model.InvalidModelException;
import org.geoint.acetate.model.NamedTypeRef;
import org.geoint.acetate.model.ResourceOperation;
import org.geoint.acetate.model.ResourceType;
import org.geoint.acetate.util.ImmutableNamedMap;

/**
 * An instance of a domain resource.
 *
 */
public class ResourceInstance extends CompositeInstance<ResourceType> {

    protected final String guid;
    protected final String version;
    protected final Optional<String> previousVersion;

    public ResourceInstance(ResourceType typeModel, String guid, String version,
            ImmutableNamedMap<InstanceRef> composites) {
        this(typeModel, guid, version, null, composites);
    }

    protected ResourceInstance(ResourceType typeModel, String guid,
            String version, String previousVersion,
            ImmutableNamedMap<InstanceRef> composites) {
        super(typeModel, composites);
        this.guid = guid;
        this.version = version;
        this.previousVersion = Optional.ofNullable(previousVersion);
    }

    /**
     * Resource instance globally unique identifier.
     *
     * @return immutable unique identifier for the resource
     */
    public String getResourceGuid() {
        return guid;
    }

    /**
     * The instance version identifier.
     *
     * @return resource version
     */
    public String getResourceVersion() {
        return version;
    }

    /**
     * The previous version of this resource.
     *
     * @return previous version resource or null if this the resource did not
     * have a previous version
     */
    public Optional<String> getPreviousResourceVersion() {
        return previousVersion;
    }

    /**
     * Returns InstanceRef for each model-defined composite, including linked
     * resources.
     * <p>
     * Note an InstanceRef will exist for each model-defined composite type even
     * if this instance does not have a composite instance.
     *
     * @return composite instances
     */
    @Override
    public Collection<InstanceRef> getComposites() {
        return super.getComposites();
    }

    /**
     * Retrieve an instance reference to a model-defined composite type,
     * including linked resources.
     * <p>
     * Note this method will always return an appropriate InstanceRef instance
     * for the composite name even if the composite instance is null for this
     * instance.
     *
     * @param compositeRefName composite reference name
     * @return composite or null
     * @throws InvalidModelException if the requested composite ref name is not
     * a valid, model-defined, reference name for a composite
     */
    @Override
    public InstanceRef findComposite(String compositeRefName)
            throws InvalidModelException {
        return super.findComposite(compositeRefName);
    }

    /**
     * Retrieve an instance reference to the model-defined resource link.
     * <p>
     * Note this method will always return a TypeInstanceRef for each link
     * defined by the resource model, even if this resource does not currently
     * link to another resource instance.
     *
     * @return collection of link instance references
     */
    public Collection<TypeInstanceRef> getLinks() {
        return typeModel.getLinks().stream()
                .map(NamedTypeRef::getName)
                .map((n) -> (TypeInstanceRef) composites.get(n))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve an instance reference for the specific link name for this
     * resource.
     *
     * @param linkName link reference name
     * @return link instance reference
     * @throws InvalidModelException if the requested link name is not defined
     * by the resource model
     */
    public TypeInstanceRef findLink(String linkName)
            throws InvalidModelException {
        typeModel.findLink(linkName)
                .orElseThrow(() -> new InvalidModelException(String.format(
                        "Link reference '%s' is not defined by the resource "
                        + "model.", linkName)));
        return (TypeInstanceRef) composites.get(linkName);
    }

    public Collection<ResourceOperation> listOperations() {
        return getModel().getOperations();
    }

    /**
     * Create an executable OperationInstance with this resource instance
     * context.
     *
     * @param operationRefName model-defined operation reference name
     * @return resource operation instance
     * @throws InvalidModelException if the requested operation name is not
     * defined by the resource model
     */
    public OperationInstance findOperation(String operationRefName)
            throws InvalidModelException {
        return OperationInstance.newInstance(this, operationRefName);
    }

}
