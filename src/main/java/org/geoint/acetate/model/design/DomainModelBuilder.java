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
package org.geoint.acetate.model.design;

import java.util.HashSet;
import java.util.Set;
import org.geoint.acetate.model.ComposedType;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainType;
import org.geoint.acetate.model.DuplicateNamedTypeException;
import org.geoint.acetate.model.InvalidModelException;
import org.geoint.acetate.model.NamedMapRef;
import org.geoint.acetate.model.NamedRef;
import org.geoint.acetate.model.NamedTypeRef;
import org.geoint.acetate.model.TypeDescriptor;
import org.geoint.acetate.model.resolve.DomainTypeResolver;
import org.geoint.acetate.util.KeyedSet;

/**
 *
 * @author steve_siebert
 */
public class DomainModelBuilder extends ModelBuilder<DomainModel> {

    private final String namespace;
    private final String version;
    private String description;
    private final KeyedSet<String, DomainType> types
            = new KeyedSet<>(DomainType::getName);
    private final Set<TypeDescriptor> dependencies = new HashSet<>();

    public DomainModelBuilder(String namespace, String version) {
        this.namespace = namespace;
        this.version = version;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getName() {
        return String.format("%s-%s", namespace, version);
    }

    public DomainModelBuilder withDescription(String desc) {
        this.description = desc;
        return this;
    }

    public DomainModelBuilder withType(DomainType type)
            throws InvalidModelException {
        if (!type.getNamespace().contentEquals(namespace)
                || !type.getVersion().contentEquals(version)) {
            throw new InvalidModelException(String.format("Type '%s' is not "
                    + "a member of of model '%s-%s'", type.toString(),
                    namespace, version));
        }

        if (!types.add(type)) {
            throw new DuplicateNamedTypeException(type.getName());
        }

        if (type instanceof ComposedType) {
            ComposedType cType = (ComposedType) type;
            cType.getComposites().stream()
                    .forEach(this::addPossibleDependency);
        }

        return this;
    }

    /**
     * If the provided reference refers to a type from a different domain model,
     * add as a dependency of this model.
     *
     * @param ref
     */
    private void addPossibleDependency(NamedRef ref) {
        if (ref instanceof NamedTypeRef) {
            addPossibleDependency((NamedTypeRef) ref);
        } else if (ref instanceof NamedMapRef) {
            NamedMapRef mRef = (NamedMapRef) ref;
            addPossibleDependency(mRef.getKeyRef());
            addPossibleDependency(mRef.getValueRef());
        }
    }

    private void addPossibleDependency(NamedTypeRef tRef) {
        TypeDescriptor td = tRef.getReferencedDescriptor();
        if (!td.getNamespace().contentEquals(this.namespace)
                || td.getVersion().contentEquals(this.version)) {
            //it is an external type
            this.dependencies.add(td);
        }
    }

    @Override
    public DomainModel createModel(DomainTypeResolver<TypeDescriptor> resolver)
            throws InvalidModelException {
        return new DomainModel(namespace, version, description,
                types, dependencies);
    }

}
