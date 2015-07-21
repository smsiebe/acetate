package org.geoint.acetate.model;

/**
 * Models a type that is used as a type member, within a method signature, etc.
 *
 * @param <T>
 */
public interface ModelTypeUse<T> extends ModelType<T> {

    /**
     * The model annotations defined at use-time.
     *
     * @return use-time model annotations or empty array
     */
    ModelAnnotation<?>[] getUseModelAnnotations();
}
