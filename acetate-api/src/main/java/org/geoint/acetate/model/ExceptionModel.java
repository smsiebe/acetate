package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.Domain;

/**
 * Models an Exception.
 * @param <T>
 */
@Domain(name = "acetate", version = 1)
public interface ExceptionModel<T extends Throwable> extends ComposedModel<T> {

}
