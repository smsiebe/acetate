package org.geoint.acetate.domain.model;

import java.util.Collection;
import java.util.Set;
import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.domain.annotation.MultiComposite;

/**
 * Models a generic Object within the data model.
 * <p>
 * A domain model object can contain both data and behavior (operations).
 *
 * @param <T>
 */
@Model(name = "objectModel", displayName = "Object Model",
        domain = DataModel.ACETATE_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public interface ObjectModel<T> extends CompositeModel<T> {

    /**
     * All object behavior, including those inherited from parent object models.
     *
     * @return all object operation models
     */
    @MultiComposite(name = "operations", itemName = "operation")
    Collection<OperationModel> getOperations();

    /**
     * Domain models from which this model extends.
     *
     * @return parent object models
     */
    @MultiComposite(name = "parents")
    Set<DataModel> getParents();

    /**
     * Object model classes that extends this model.
     *
     * @return specialized types of this model
     */
    @MultiComposite(name = "specialized")
    Collection<ObjectModel<? extends T>> getSpecialized();

}
