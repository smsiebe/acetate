package org.geoint.acetate.data.transform;

import org.geoint.acetate.model.ModelContextPath;
import org.geoint.acetate.model.DomainModel;

/**
 * Converts bytes to objects and objects to bytes.
 *
 * All codecs must be thread-safe.
 *
 * @param <T> data type
 */
public abstract class ComplexObjectCodec<T> extends ObjectCodec<T> {

    protected final DomainModel model;
    protected final ModelContextPath path;

    public ComplexObjectCodec(DomainModel model, ModelContextPath path) {
        this.model = model;
        this.path = path;
    }

}
