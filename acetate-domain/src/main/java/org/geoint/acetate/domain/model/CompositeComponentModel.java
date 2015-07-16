package org.geoint.acetate.domain.model;

import java.util.Collection;
import javax.validation.ConstraintValidator;
import javax.validation.ValidationException;
import org.geoint.acetate.domain.annotation.Accessor;
import org.geoint.acetate.domain.annotation.Composite;
import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.domain.annotation.MultiComposite;
import org.geoint.acetate.domain.annotation.Query;

/**
 * A contextually-defined decorated data model component.
 *
 * @param <T>
 * @param <M>
 */
@Model(name = "compositeComponentModel", displayName = "Composite Component Model",
        domain = DataModel.ACETATE_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public interface CompositeComponentModel<T, M extends DataModel<T>>
        extends DataModel<T> {

    /**
     * Return the data model for this model within this context.
     *
     * @return base model without contextual information
     */
    @Accessor(name = "getModel")
    @Composite(name = "baseModel")
    M getContextualModel();

    /**
     * Data constraints applied to the data in this context only.
     *
     * @return constraints
     */
    @MultiComposite(name = "constraintModels", itemName = "constraintModel")
    Collection<ConstraintModel<?, T>> getConstraintModels();

    /**
     * Indicates if there may be more than one composite component instance
     * assigned to this context.
     *
     * @return true of this composite can have multiple instances of the data
     */
    @Composite(name = "collection", displayName = "Composite Collection")
    boolean isMulti();

    /**
     * Return the bean validation constraints for this model.
     *
     * @return bean validation validators
     */
    @Query
    Collection<ConstraintValidator<?, T>> getValidators();

    /**
     * Validate the provided data instance against all constraints.
     *
     * @param obj
     * @throws ValidationException
     */
    @Query
    void validate(T obj) throws ValidationException;

}
