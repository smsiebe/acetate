package org.geoint.acetate.impl.meta.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import org.geoint.acetate.meta.model.ObjectModel;
import org.geoint.acetate.meta.model.ReturnModel;
import org.geoint.acetate.meta.model.ThrowableModel;

/**
 *
 */
public final class ImmutableReturnModel<R> implements ReturnModel<R> {

    private final ObjectModel<R> model;
    private final Set<ThrowableModel<?>> exceptionModels;

    public ImmutableReturnModel(ObjectModel<R> model,
            Set<ImmutableThrowableModel<?>> exceptionModels) {
        this.model = model;
        this.exceptionModels = Collections.unmodifiableSet(exceptionModels);
    }

    @Override
    public ObjectModel<R> getModel() {
        return model;
    }

    @Override
    public Set<ThrowableModel<?>> getExceptions() {
        return exceptionModels;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.model);
        hash = 23 * hash + Objects.hashCode(this.exceptionModels);
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
        final ImmutableReturnModel<?> other = (ImmutableReturnModel<?>) obj;
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Objects.equals(this.exceptionModels, other.exceptionModels)) {
            return false;
        }
        return true;
    }

}
