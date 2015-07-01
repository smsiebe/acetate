package org.geoint.acetate.impl.domain.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.geoint.acetate.domain.model.ObjectModel;
import org.geoint.acetate.domain.model.ReturnModel;
import org.geoint.acetate.domain.model.ExceptionModel;

/**
 *
 */
final class ImmutableReturnModel implements ReturnModel {

    private final Optional<ObjectModel> model;
    private final Set<ExceptionModel> exceptionModels;

    /**
     *
     * @param model optional return type model (may be null)
     * @param exceptionModels exceptions that can be thrown, may be empty
     * collection
     */
    public ImmutableReturnModel(ObjectModel model,
            Set<ImmutableThrowableModel> exceptionModels) {
        this.model = Optional.ofNullable(model);
        this.exceptionModels = Collections.unmodifiableSet(exceptionModels);
    }

    @Override
    public Optional<ObjectModel> getModel() {
        return model;
    }

    @Override
    public Set<ExceptionModel> getExceptions() {
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
        final ImmutableReturnModel other = (ImmutableReturnModel) obj;
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Objects.equals(this.exceptionModels, other.exceptionModels)) {
            return false;
        }
        return true;
    }

}
