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
package org.geoint.acetate.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import org.geoint.acetate.functional.ThrowingFunction;
import org.geoint.acetate.model.DomainBuilder.EventBuilder;
import org.geoint.acetate.serialization.TypeCodec;
import org.geoint.acetate.spi.model.DomainModelProvider;

/**
 * Domain model registry.
 * <p>
 * DomainRegistry instances are <b>NOT</b> thread-safe, applications must
 * implement external locking if this is required.
 *
 * @author steve_siebert
 */
public class DomainRegistry implements DomainModelProvider, TypeResolver {

    private final Set<DomainModel> models = new HashSet<>();

    /**
     * Returns a domain builder, registering the domain on successful build.
     *
     * @param namespace domain namespace
     * @param version domain version
     * @return domain builder
     * @throws DuplicateDomainException thrown if the specified domain is
     * already registered
     */
    public DomainBuilder builder(String namespace, String version)
            throws DuplicateDomainException {
        if (findModel(namespace, version).isPresent()) {
            throw new DuplicateDomainException(namespace, version);
        }
        return new DomainBuilder(namespace, version,
                
                //add a resolver that is backed by this registry
                (ns, v, tn) -> models.parallelStream()
                .filter((d) -> d.getNamespace().contentEquals(namespace))
                .filter((d) -> d.getVersion().contentEquals(version))
                .flatMap(DomainModel::typeStream)
                .filter((t) -> t.getName().contentEquals(tn))
                .findFirst());
    }

    /**
     * Returns an unmodifiable collection of domain models known to this
     * registry.
     *
     * @return domain models
     */
    @Override
    public Set<DomainModel> getDomainModels() {
        return Collections.unmodifiableSet(models);
    }

    public void register(DomainModel model) throws DuplicateDomainException {
        if (models.contains(model)) {
            throw new DuplicateDomainException(model.getNamespace(),
                    model.getVersion());
        }
        this.models.add(model);
    }

    /**
     * Does a duplicate domain model double-check, once prior to retrieving
     * (creating) the model from the provided supplier and once before
     * registration of the supplied model.
     * <p>
     * This method is useful when the model generation process is known to be
     * time consuming and there may be competing domain providers.
     *
     * @param namespace domain namespace
     * @param version domain version
     * @param modelSupplier domain model supplier
     * @throws DuplicateDomainException if the domain is already registered
     */
    public void register(String namespace, String version,
            Supplier<DomainModel> modelSupplier)
            throws DuplicateDomainException {

        //pre-model retrieval
        if (findModel(namespace, version).isPresent()) {
            throw new DuplicateDomainException(namespace, version);
        }

        register(modelSupplier.get()); //checks for duplicates before registration
    }

    public Optional<DomainModel> findModel(String namespace, String version) {
        return models.stream()
                .filter((m) -> m.getNamespace().contentEquals(namespace))
                .filter((m) -> m.getVersion().contentEquals(version))
                .findFirst();
    }

    @Override
    public Optional<DomainType> findType(String namespace, String version,
            String typeName) {
        return findModel(namespace, version)
                .flatMap((m) -> m.typeStream()
                        .filter((t) -> t.getName().contentEquals(typeName))
                        .findFirst());
    }

}
