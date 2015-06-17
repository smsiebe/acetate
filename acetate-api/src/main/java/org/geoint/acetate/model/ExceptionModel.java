package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.Model;

/**
 * Models an Exception.
 * @param <T>
 */
@Model(name="", domainName="acetate", domainVersion=1)
public interface ExceptionModel<T extends Throwable> extends ComposedModel<T> {

}
