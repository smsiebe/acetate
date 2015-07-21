package org.geoint.acetate.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;

/**
 * A member of a {@link ModelType}.
 */
public interface ModelMember extends ModelElement, Member {

    /**
     * The class name which declared this member.
     *
     * @return name of the declaring class
     */
    String getDeclaringClassName();

    /**
     * Returns an instance of the class this model member is defined.
     *
     * @return declaring class type
     */
    @Override
    Class<?> getDeclaringClass();

    /**
     * Check if the declaring class is annotated by the specified model
     * annotation.
     *
     * @param modelAnnotation
     * @return true if the declaring class is a type of the specified model
     */
    boolean isDeclaredByModel(Class<? extends Annotation> modelAnnotation);

}
