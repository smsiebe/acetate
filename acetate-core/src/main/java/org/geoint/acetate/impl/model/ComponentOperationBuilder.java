package org.geoint.acetate.impl.model;

import java.lang.reflect.Method;

/**
 * Builder used to programmatically define a component operation.
 *
 */
public interface ComponentOperationBuilder {

    /**
     * Sets a description for this operation.
     *
     * @param description operation description
     * @return this builder (fluid interface)
     */
    ComponentOperationBuilder description(String description);

    /**
     * Provides a method reference to the operation.
     *
     * @param m
     * @return this builder (fluid interface)
     */
    ComponentOperationBuilder method(Method m);

    /**
     * Set the return type of the operation.
     *
     * @param componentName domain-unique component name
     * @return return context builder, allowing the context-specific settings
     */
    ContextBuilder<?> returns(String componentName);

    /**
     * Set the return type, which is a collection, of the operation.
     *
     * @param componentName domain-unique component name supported by the
     * collection
     * @return return context builder, allowing the context-specific settings
     */
    ContextBuilder<?> returnsCollection(String componentName);

    /**
     * Sets the return type, which is a map, of the operation.
     *
     * @param keyComponentName domain-unique component name for the map key
     * @param valueComponentName domain-unique component name for the map value
     * @return return context builder, allowing the context-specific settings
     */
    MapContextBuilder<?, ?> returnsMap(String keyComponentName,
            String valueComponentName);

    /**
     * Add a parameter to the operation signature.
     *
     * @param componentName domain-unique component name
     * @param paramName name of parameter, which must be unique within this
     * operation
     * @return parameter context builder, allowing context-specific settings
     */
    ContextBuilder<?> parameter(String componentName, String paramName);

    /**
     * Adds a collection parameter to the operation signature.
     *
     * @param componentName domain-unique component name supported by the
     * collection
     * @param paramName name of parameter, which must be unique within this
     * operation
     * @return parameter context builder, allowing context-specific settings
     */
    ContextBuilder<?> collectionParameter(String componentName,
            String paramName);

    /**
     * Adds a map parameter to the operation signature.
     *
     * @param keyComponentName domain-unique component name for the map key
     * @param valueComponentName domain-unique component name for the map value
     * @param paramName name of parameter, which must be unique within this
     * operation
     * @return parameter context builder, allowing context-specific settings
     */
    ContextBuilder<?> mapParameter(String keyComponentName,
            String valueComponentName, String paramName);
}
