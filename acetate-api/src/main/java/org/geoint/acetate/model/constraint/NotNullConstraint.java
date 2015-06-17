package org.geoint.acetate.model.constraint;

import org.geoint.acetate.model.ObjectModel;

/**
 * Ensures that the data is not null.
 *
 * This class is thread-safe.
 */
public final class NotNullConstraint implements DataConstraint {

    private static final NotNullConstraint INSTANCE = new NotNullConstraint();

    public NotNullConstraint() {

    }

    public static NotNullConstraint getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> boolean validate(ObjectModel<T> model, T data) {
        return data != null;
    }

}
