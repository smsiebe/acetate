package org.geoint.acetate.impl.meta.model;

/**
 * Fluid API to create an ObjectModel instance.
 *
 * @param <T>
 */
public interface ObjectModelBuilder<T> {

    /**
     * Add a parent model to the object model.
     * 
     * @param parentClass
     * @return this builder
     */
    ObjectModelBuilder<T> specializes(Class<? super T> parentClass);

    /**
     * Add a {@link Meta metamodel attribute} to this object model.
     * 
     * @param name
     * @param value
     * @return this builder
     */
    ObjectModelBuilder<T> withAttribute(String name, String value);

    /**
     * Add an operation to this object model.
     * 
     * @param operationName
     * @return new operation builder
     */
    OperationModelBuilder<?> withOperation(String operationName);

}
