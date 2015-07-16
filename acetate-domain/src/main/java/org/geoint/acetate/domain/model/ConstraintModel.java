package org.geoint.acetate.domain.model;

import java.util.Collection;
import javax.validation.ConstraintValidator;
import org.geoint.acetate.domain.annotation.Accessor;
import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.domain.annotation.MultiComposite;
import org.geoint.acetate.domain.annotation.Query;
import org.geoint.acetate.model.constraint.DataConstraint;

/**
 * Models a domain-defined data constraint.
 *
 * @param <V> java class of the constraint
 * @param <T> type of data validated by this constraint
 */
@Model(name = "constraint", displayName = "Constraint Model",
        domain = DataModel.ACETATE_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public interface ConstraintModel<V, T> extends DataModel<T> {

    /**
     * Domain name of the constraint.
     * <p>
     * For Bean Validation custom validators, the domain name of the constraint
     * must be the same as the {@link DataConstraint#name()} in order for the
     * constraint implementation to be discovered by the domain model.
     *
     * @return domain name of the constraint
     */
    @Accessor(name = "constraintName", displayName = "Constraint Name")
    String getName();

    /**
     * Data model of the constraint properties.
     * <p>
     * For Bean Validation custom validators, constraint properties are mapped
     * to the custom annotation attribute values.
     *
     * @return domain defined constraint properties
     */
    @Accessor
    @MultiComposite(name = "properties", itemName = "property")
    Collection<CompositeComponentModel<?, ?>> getProperties();

    @Query
    ConstraintValidator<?, T> getValidator();
}
