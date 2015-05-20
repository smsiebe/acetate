package org.geoint.acetate.model.constraint;

import org.geoint.acetate.model.DomainObject;

/**
 * Ensures that the data is not null.
 *
 * This class is thread-safe.
 */
public final class NotNullConstraint implements ComponentConstraint {

    private static final NotNullConstraint INSTANCE = new NotNullConstraint();

    private NotNullConstraint() {

    }

    public static NotNullConstraint getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> boolean validate(DomainObject<T> model, T data) {
        return data != null;
    }

}
