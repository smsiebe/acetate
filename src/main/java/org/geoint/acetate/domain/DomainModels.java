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
package org.geoint.acetate.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;
import org.geoint.acetate.domain.provider.DomainProvider;

/**
 * Loads available DomainModel instances from {@link DomainProvider providers}.
 * <p>
 * DomainModels does not provider any model caching functions and is a
 * responsibility of the DomainProvider, if appropriate.
 *
 * @author steve_siebert
 */
public final class DomainModels {

    private static final Set<DomainProvider> providers;

    static {
        providers = new HashSet<>();

        ServiceLoader<DomainProvider> slProviders = ServiceLoader.load(DomainProvider.class);
        slProviders.forEach(providers::add);
    }

    public static void addProvider(DomainProvider provider) {
        providers.add(provider);
    }

    public static Collection<DomainModel> loadModels() {
        return providers.stream()
                .flatMap((p) -> p.getDomainModels().stream())
                .collect(Collectors.toList());
    }

}
