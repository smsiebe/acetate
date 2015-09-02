/*
 * Copyright 2015 Expression project.organization is undefined on line 4, column 57 in Templates/Licenses/license-apache20.txt..
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.geoint.acetate.domain.reflect.MutableDomainModel;
import org.geoint.acetate.domain.reflect.MutableOntology;
import org.geoint.acetate.provider.DomainProvider;

/**
 * Loads available DomainModel instances from {@link DomainProvider providers}.
 * <p>
 * DomainModels does not provide any model caching functions and is a
 * responsibility of the DomainProvider, if appropriate.
 *
 * @author steve_siebert
 */
public final class DomainModels {

    private static final Set<DomainProvider> providers;
    private static final Logger logger
            = Logger.getLogger(DomainModels.class.getName());

    private final Set<DomainModel> models;

    static {
        //providers = Collections.synchronizedSet(new HashSet<>());
        providers = new HashSet<>(); //manually synchronized

        //synchronized (providers) { //I really don't think this is necessary
        ServiceLoader<DomainProvider> slProviders = ServiceLoader.load(DomainProvider.class);
        slProviders.forEach(providers::add);
        //}

        logger.log(Level.FINE, () -> String.format(
                "Domain model providers loaded from ServiceLoader: [%s]",
                StreamSupport.stream(slProviders.spliterator(), false)
                .map((p) -> p.getClass().getCanonicalName())
                .collect(Collectors.joining(",")))
        );
    }

    private DomainModels(Set<DomainModel> models) {
        this.models = Collections.unmodifiableSet(models);
    }

    /**
     * Load domain models from the configured domain model providers.
     *
     * @return domain models loaded from providers
     * @throws DomainModelException thrown if there was a terminal problem
     * resolving the domain models
     */
    public static DomainModels loadModels()
            throws DomainModelException {
        synchronized (providers) {
            return loadModels(providers.toArray(
                    new DomainProvider[providers.size()])
            );
        }
    }

    /**
     * Load domain models from the provided providers (does not include static
     * configured providers).
     *
     * @param providers providers from which to load models
     * @return domain models resolved from providers
     * @throws DomainModelException thrown if there was a terminal problem
     * resolving the domain models
     */
    public static DomainModels loadModels(DomainProvider... providers)
            throws DomainModelException {
        return merge(Arrays.stream(providers)
                .map((p) -> {
                    logger.log(Level.FINE, () -> String.format(
                                    "Loading domain models from provider '%s'",
                                    p.getClass().getCanonicalName()));
                    return p;
                })
                .flatMap((p) -> p.getDomainModels().stream())
                .collect(Collectors.toList()));
    }

    public static void addProvider(DomainProvider provider) {
        synchronized (providers) {
            providers.add(provider);
        }
    }

    public Set<DomainModel> getModels() {
        return models;
    }

    public Set<DomainModel> getModels(String domainName) {
        return getModels().stream()
                .filter((m) -> m.getDomainName().equalsIgnoreCase(domainName))
                .collect(Collectors.toSet());
    }

    public Optional<DomainModel> getModel(String domainName, String domainVersion) {
        return getModels().stream()
                .filter((m) -> m.getDomainName().equalsIgnoreCase(domainName))
                .filter((m) -> m.getDomainVersion().equalsIgnoreCase(domainVersion))
                .findAny();
    }

    private static DomainModels merge(Collection<DomainModel> models)
            throws DomainModelException {
        Set<MutableDomainModel> mergedModels = new HashSet<>();
        for (DomainModel dm : models) {
            Optional<MutableDomainModel> domainCollision = mergedModels.stream()
                    .filter((mm) -> mm.isSameDomain(dm))
                    .findFirst();

            if (domainCollision.isPresent()) {
                domainCollision.get().merge(dm);
            } else {
                mergedModels.add(createMergedModel(mergedModels, dm));
            }
        }

        //this.models = toModels(mergedModels); //TODO convert to immutable models
        return new DomainModels(mergedModels.stream() //needed for generics =(
                .map((m) -> (DomainModel) m)
                .collect(Collectors.toSet()));
    }

    private static MutableDomainModel createMergedModel(
            Set<MutableDomainModel> mergedModels, DomainModel domainModel)
            throws IllegalModelException {
        if (Ontology.class.isAssignableFrom(domainModel.getClass())) {
            return new MutableOntology((Ontology) domainModel,
                    mergedModels);
        } else {
            return new MutableDomainModel(domainModel);
        }
    }

}
