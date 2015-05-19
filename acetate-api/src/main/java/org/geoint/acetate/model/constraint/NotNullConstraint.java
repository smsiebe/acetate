package org.geoint.acetate.model.constraint;

import gov.ic.geoint.acetate.bind.AnnotationDataFactory;
import gov.ic.geoint.acetate.bind.DataBindException;
import java.lang.annotation.Annotation;
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

    /**
     * Uses to construct an instance using the {@link NotNull} constraint 
     * annotation.
     */
    public static class NotNullConstraintAnnotationConstructor
            implements AnnotationDataFactory<ComponentConstraint> {

        @Override
        public ComponentConstraint getInstance(Annotation... annotations)
                throws DataBindException {
            return NotNullConstraint.INSTANCE;
        }
    }

}
