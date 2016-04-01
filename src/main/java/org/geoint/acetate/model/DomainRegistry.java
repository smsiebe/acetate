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

import org.geoint.acetate.spi.model.TypeResolver;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.function.Supplier;
import org.geoint.acetate.spi.model.DomainModelProvider;

/**
 * Domain model registry.
 * <p>
 * Instances of the DomainRegistry are thread-safe.
 *
 * @author steve_siebert
 */
public class DomainRegistry implements DomainModelProvider, TypeResolver {

    public static final DomainRegistry DEFAULT = new DomainRegistry();

    private final Set<DomainModel> registered
            = Collections.synchronizedSet(new HashSet<>());

    public DomainRegistry() {
    }

    /**
     * Load domain models from the {@link DomainModelProvider providers}
     * discovered by the {@link ServiceLoader} on the specified classpath.
     *
     * @param cl
     */
    public void loadProviders(ClassLoader cl) {
        Iterator<DomainModelProvider> providers
                = ServiceLoader.load(DomainModelProvider.class).iterator();

        while (providers.hasNext()) {
            loadProvider(providers.next());
        }
    }

    /**
     * Load domain models from the {@link DomainModelProviders providers}
     * discovered by the {@link ServiceLoader} on the
     * {@link Thread#contextClassLoader current thread context classloader}.
     *
     */
    public void loadProviders() {
        loadProviders(Thread.currentThread().getContextClassLoader());
    }

    /**
     * Loads domain models from the provider.
     *
     * @param provider domain model provider
     */
    public void loadProvider(DomainModelProvider provider) {
        for (DomainModel m : provider.getDomainModels()) {
            try {
                register(m);
                //TODO publish provider results as event
            } catch (InvalidModelException ex) {
                //TODO publish event indicating there was a problem with a model
            }
        }
    }

    /**
     * Returns a domain builder that will auto-register the defined domain on
     * successful build.
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
                (ns, v, tn) -> registered.parallelStream()
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
        return Collections.unmodifiableSet(registered);
    }

    /**
     * Register the provided domain model.
     *
     * @param model domain model to register
     * @throws InvalidModelException if there was a problem with the model, or
     * there was a naming collision with an already registered model
     */
    public void register(DomainModel model)
            throws InvalidModelException {
        if (registered.contains(model)) {
            throw new DuplicateDomainException(model.getNamespace(),
                    model.getVersion());
        }
        this.registered.add(model);

        //TODO publish registration event
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
            throws DuplicateDomainException, InvalidModelException {

        //pre-model retrieval
        if (findModel(namespace, version).isPresent()) {
            throw new DuplicateDomainException(namespace, version);
        }

        //TODO publish registration event
    }

    public Optional<DomainModel> findModel(String namespace, String version) {
        return registered.stream()
                .filter((m) -> m.getNamespace().contentEquals(namespace))
                .filter((m) -> m.getVersion().contentEquals(version))
                .findFirst();
    }

    @Override
    public Optional<DomainType> resolve(String namespace, String version,
            String typeName) {
        return findModel(namespace, version)
                .flatMap((m) -> m.typeStream()
                        .filter((t) -> t.getName().contentEquals(typeName))
                        .findFirst());
    }

}
