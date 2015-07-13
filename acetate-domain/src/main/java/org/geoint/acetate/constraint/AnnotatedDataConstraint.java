package org.geoint.acetate.constraint;

import java.lang.annotation.Annotation;

/**
 *
 * @param <T> data type supported by constraint
 * @param <A> annotation type used to define constraint
 */
public abstract class AnnotatedDataConstraint<T, A extends Annotation>
        implements DataConstraint<T> {

    public AnnotatedDataConstraint(A annotation) {
    }

}
