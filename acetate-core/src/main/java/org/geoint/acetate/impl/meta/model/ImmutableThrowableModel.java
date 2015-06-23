package org.geoint.acetate.impl.meta.model;

import java.util.Objects;
import org.geoint.acetate.meta.model.ThrowableModel;

/**
 *
 * @param <E>
 */
public final class ImmutableThrowableModel<E> implements ThrowableModel<E> {

    private final Class<E> throwableType;

    public ImmutableThrowableModel(Class<E> throwableType) {
        this.throwableType = throwableType;
    }

    @Override
    public Class<E> getExceptionClass() {
        return throwableType;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.throwableType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImmutableThrowableModel<?> other = (ImmutableThrowableModel<?>) obj;
        if (!Objects.equals(this.throwableType, other.throwableType)) {
            return false;
        }
        return true;
    }

}
