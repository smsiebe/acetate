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

import org.geoint.acetate.model.resolve.MapTypeResolver;
import org.geoint.acetate.model.resolve.HierarchicalTypeResolver;
import java.util.Set;
import java.util.function.BiFunction;
import org.geoint.acetate.functional.ThrowingConsumer;
import org.geoint.acetate.functional.ThrowingFunction;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainType;
import org.geoint.acetate.model.DuplicateNamedTypeException;
import org.geoint.acetate.model.InvalidModelException;
import org.geoint.acetate.model.TypeDescriptor;
import org.geoint.acetate.model.resolve.DomainTypeResolver;
import org.geoint.acetate.model.resolve.UnresolvedException;
import org.geoint.acetate.util.KeyedSet;

/**
 * Fluid interface used define multiple domain types.
 * <p>
 * The models that created from the builder are immutable. Any further change to
 * the DomainBuilder instance will not change the model instances that were
 * previously created.
 * <p>
 * DomainBuilder, and model builders returned form it, are <b>NOT</b> thread
 * safe.
 *
 * @author steve_siebert
 */
public class DomainBuilder {

    private final String defaultNamespace;
    private final String defaultVersion;
    private final KeyedSet<TypeDescriptor, TypeBuilder<?, ?>> typeBuilders
            = new KeyedSet<>(TypeBuilder::getDescriptor);

    public DomainBuilder(String defaultNamespace, String defaultVersion) {
        this.defaultNamespace = defaultNamespace;
        this.defaultVersion = defaultVersion;
    }

    public String getDefaultNamespace() {
        return defaultNamespace;
    }

    public String getDefaultVersion() {
        return defaultVersion;
    }

    public ValueBuilder defineValue(String typeName)
            throws InvalidModelException, IllegalStateException {
        return defineValue(defaultNamespace, defaultVersion, typeName);
    }

    public ValueBuilder defineValue(String namespace, String version, String typeName)
            throws InvalidModelException, IllegalStateException {
        return defineType(ValueBuilder::new, namespace, version, typeName);
    }

    public void defineValueIfAbsent(String typeName,
            ThrowingConsumer<ValueBuilder, InvalidModelException> callback)
            throws InvalidModelException {
        defineValueIfAbsent(defaultNamespace, defaultVersion, typeName, callback);
    }

    public void defineValueIfAbsent(String namespace, String version, String typeName,
            ThrowingConsumer<ValueBuilder, InvalidModelException> callback)
            throws InvalidModelException {
        defineValueIfAbsent(new TypeDescriptor(namespace, version, typeName),
                callback);
    }

    public void defineValueIfAbsent(TypeDescriptor valueDesc,
            ThrowingConsumer<ValueBuilder, InvalidModelException> callback)
            throws InvalidModelException {
        if (!typeBuilders.findByKey(valueDesc).isPresent()) {
            ValueBuilder vb = new ValueBuilder(this::registerType, valueDesc);
            callback.consume(vb);
        }
    }

    public EventBuilder defineEvent(String typeName)
            throws InvalidModelException {
        return defineEvent(defaultNamespace, defaultVersion, typeName);
    }

    public EventBuilder defineEvent(String namespace, String version,
            String typeName) throws InvalidModelException {
        return defineType(EventBuilder::new, namespace, version, typeName);
    }

    public void defineEventIfAbsent(String typeName,
            ThrowingConsumer<EventBuilder, InvalidModelException> callback)
            throws InvalidModelException {
        defineEventIfAbsent(defaultNamespace, defaultVersion, typeName, callback);
    }

    public void defineEventIfAbsent(String namespace, String version, String typeName,
            ThrowingConsumer<EventBuilder, InvalidModelException> callback)
            throws InvalidModelException {
        defineEventIfAbsent(new TypeDescriptor(namespace, version, typeName),
                callback);
    }

    public void defineEventIfAbsent(TypeDescriptor td,
            ThrowingConsumer<EventBuilder, InvalidModelException> callback)
            throws InvalidModelException {
        if (!typeBuilders.findByKey(td).isPresent()) {
            EventBuilder eb = new EventBuilder(this::registerType, td);
            callback.consume(eb);
        }
    }

    public ResourceBuilder defineResource(String typeName)
            throws InvalidModelException {
        return defineResource(defaultNamespace, defaultVersion, typeName);
    }

    public ResourceBuilder defineResource(String namespace, String version, String typeName)
            throws InvalidModelException {
        return defineType(ResourceBuilder::new, namespace, version, typeName);
    }

    public void defineResourceIfAbsent(String typeName,
            ThrowingConsumer<ResourceBuilder, InvalidModelException> callback)
            throws InvalidModelException {
        defineResourceIfAbsent(defaultNamespace, defaultVersion, typeName, callback);
    }

    public void defineResourceIfAbsent(String namespace, String version, String typeName,
            ThrowingConsumer<ResourceBuilder, InvalidModelException> callback)
            throws InvalidModelException {
        defineResourceIfAbsent(new TypeDescriptor(namespace, version, typeName),
                callback);
    }

    public void defineResourceIfAbsent(TypeDescriptor td,
            ThrowingConsumer<ResourceBuilder, InvalidModelException> callback)
            throws InvalidModelException {
        if (!typeBuilders.findByKey(td).isPresent()) {
            ResourceBuilder rb = new ResourceBuilder(this::registerType, td);
            callback.consume(rb);
        }
    }

    private <B extends TypeBuilder> B defineType(
            BiFunction<ThrowingFunction<B, DomainBuilder, InvalidModelException>, TypeDescriptor, B> typeBuilderConstructor,
            String namespace, String version, String typeName)
            throws InvalidModelException {
        final TypeDescriptor td = new TypeDescriptor(namespace, version, typeName);
        if (typeBuilders.findByKey(td).isPresent()) {
            throw new DuplicateNamedTypeException(typeName);
        }
        return typeBuilderConstructor.apply(this::registerType, td);
    }

    /**
     * Method reference {@link ThrowingFunction callback} that is called when a 
     * {@link TypeBuilder#complete() } method is called, registering the type
     * builder with the domain builder and returning the domain builder (this
     * keeps the API quite fluid).
     *
     * @param type domain type builder
     * @return this builder
     * @throws InvalidModelException if the type
     */
    private DomainBuilder registerType(TypeBuilder type)
            throws InvalidModelException {
        if (!typeBuilders.add(type)) {
            throw new DuplicateNamedTypeException(type.getName());
        }
        return this;
    }

    /**
     * Creates the domain types defined by this builder, throwing an
     * InvalidModelException if there were any unresolved types.
     *
     * @return domain types defined by the builder
     * @throws InvalidModelException
     */
    public Set<DomainType> createTypes() throws InvalidModelException {
        return createTypes(null);
    }

    /**
     * Creates the domain types defined by this builder or resolved by the
     * provided resolver, throwing an InvalidModelException if there are
     * remaining unresolved models.
     *
     * @param resolver resolver used to find existing models; may be null
     * @return domain types defined by the builder and/or the provided resolver
     * @throws InvalidModelException
     */
    public Set<DomainType> createTypes(final DomainTypeResolver<TypeDescriptor> resolver)
            throws InvalidModelException {

        /*
             * types are resolved in the following sequence, returning the first
             * resolution found:
             *  1) if the type is for this domain, check the collection of 
             *     types that have already been created during this complete process
             *  2) if a DomainTypeResolver was provided (constructor), attempt to 
             *     resolveType from it
             *  3) if the type is for this domain, attempt to resolveType the type 
             *     from any remaining domain type definitions 
             *  4) throw UnknownTypeException
         */
        final DomainTypeResolver<TypeDescriptor> builderResolver
                = builderResolver(resolver);
        return createTypeWithResolver(builderResolver);
    }

    private Set<DomainType> createTypeWithResolver(
            DomainTypeResolver<TypeDescriptor> resolver) throws InvalidModelException {

        return typeBuilders.rekeySet((b) -> b.createModel(resolver));
    }

    private DomainTypeResolver<TypeDescriptor> builderResolver(DomainTypeResolver<TypeDescriptor> resolver) {
        return HierarchicalTypeResolver.newHierarchy(new TypeBuilderResolver())
                .optionalChild(resolver)
                .addChild(new MapTypeResolver());
    }

    public Set<DomainModel> createModels() throws InvalidModelException {
        return createModels(null);
    }

    public Set<DomainModel> createModels(DomainTypeResolver<TypeDescriptor> resolver)
            throws InvalidModelException {

        KeyedSet<String, DomainModelBuilder> models = new KeyedSet<>(DomainModelBuilder::getName);
        DomainTypeResolver<TypeDescriptor> builderResolver = builderResolver(resolver);

        createTypes(builderResolver).stream()
                .forEach((t) -> {

                    final String modelName
                            = String.format("%s-%s", t.getNamespace(), t.getVersion());

                    DomainModelBuilder mb = models.addIfAbsent(modelName,
                            () -> new DomainModelBuilder(
                                    t.getNamespace(),
                                    t.getVersion()));

                    try {
                        mb.withType(t);
                    } catch (InvalidModelException ex) {
                        //can't get here, we created the model to match the type namespace and version
                    }
                    models.add(mb);
                });

        return models.rekeySet((b) -> b.createModel(builderResolver));
    }

    /**
     * Attempts to resolver a DomainType from the registered TypeBuilders.
     */
    private class TypeBuilderResolver implements DomainTypeResolver<TypeDescriptor> {

        @Override
        public DomainType resolve(TypeDescriptor td) throws UnresolvedException {
            try {
                return typeBuilders.stream()
                        .filter((b) -> b.typeDescriptor.equals(td))
                        .findFirst()
                        .orElseThrow(() -> new UnresolvedException(String.format("Unable to "
                                + "resolve type builder for type '%s'.", td.toString())))
                        .createModel(this);
            } catch (InvalidModelException ex) {
                throw new UnresolvedException("Unable to resolve domain type "
                        + "from designed models.");
            }

        }

    }
}
