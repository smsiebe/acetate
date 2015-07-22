package org.geoint.acetate.model;

import java.lang.annotation.Annotation;

/**
 * Model type (class/interface) definition.
 *
 * @param <T> type
 */
public interface ModelType<T> extends ModelElement {

    /**
     * All methods that are defined with any model annotation.
     *
     * @return methods which are defined with a model annotation
     */
    ModelMethod[] getModelMethods();

    /**
     * All methods annotated with the specified model annotation.
     *
     * @param modelAnnotation
     * @return methods defined with the specified model annotation or empty
     * array
     */
    ModelMethod[] getModelMethods(Class<? extends Annotation> modelAnnotation);

    /**
     * All fields annotated with any model annotation.
     *
     * @return fields annotated with a model annotation or an empty array
     */
    ModelField<?>[] getModelFields();

    /**
     * All fields annotated with the specified model annotation.
     *
     * @param modelAnnotation
     * @return fields annotated with the specified model annotation or empty
     * array
     */
    ModelField<?>[] getModelFields(Class<? extends Annotation> modelAnnotation);

    /**
     * All members of this type that are annotated with at least one metamodel
     * annotation.
     *
     * @return all members that are annotated with a metamodel annotation or
     * empty array
     */
    ModelMember[] getModelMembers();

    /**
     * All members of this type which are annotated with the specified model
     * annotation.
     *
     * @param modelAnnotation
     * @return all member models annotated with this model annotation or an
     * empty array
     */
    ModelMember[] getModelMembers(Class<? extends Annotation> modelAnnotation);

    /**
     * ModelProvider provided Class of this model type.
     *
     * @return class of the model type
     */
    Class<?> getType();

    /**
     * Class name of the model type.
     *
     * @return model type class name
     */
    String getTypeName();

    /**
     * Subclasses of the model type.
     *
     * @return subclasses of the model type
     */
    ModelType<? extends T>[] getSubclasses();
}
