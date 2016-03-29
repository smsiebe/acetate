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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.spi.model.DomainModelProvider;

/**
 * Discovers and manages known {@link DomainModel domains}.
 *
 * @author steve_siebert
 */
public final class DomainModels {

    private static final DomainModels JVM_DEFAULT
            = getInstance(DomainModels.class.getClassLoader());
    private static final Logger LOGGER
            = Logger.getLogger(DomainModels.class.getName());

    private final Set<DomainModelProvider> modelProviders = new HashSet<>();
    private final Set<DomainModel> models = new HashSet<>();

    private DomainModels() {
    }

    public static DomainModels getDefault() {
        return JVM_DEFAULT;
    }

    public static DomainModels getInstance(ClassLoader cl) {
        Iterator<DomainModelProvider> providers
                = ServiceLoader.load(DomainModelProvider.class).iterator();

        DomainModels models = new DomainModels();

        while (providers.hasNext()) {
            models.addProvider(providers.next());
        }

        return models;
    }

    public void addProvider(DomainModelProvider provider) {

        synchronized (modelProviders) {
            if (modelProviders.contains(provider)) {
                return;
            } else {
                modelProviders.add(provider);
            }
        }

        //add the models, if it isn't already defined
        provider.getDomainModels().stream()
                .parallel()
                .forEach((m) -> {
                    synchronized (models) {
                        if (models.contains(m)) {
                            LOGGER.severe(String.format("Provider '%s' attempted "
                                    + "to provide domain model '%s', but this "
                                    + "model already exists.  Skipping duplicate "
                                    + "model.", provider.getClass().getName(),
                                    m.toString()));
                        } else {
                            models.add(m);
                        }
                    }
                });
    }

    public Optional<DomainModel> getModel(String namespace, String version) {
        return stream()
                .filter((m) -> m.getNamespace().contentEquals(namespace)
                        && m.getVersion().contentEquals(version)
                ).findFirst();
    }

    public Stream<DomainModel> stream() {
        return models.stream();
    }
}
