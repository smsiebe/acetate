package org.geoint.acetate.domain.model;

import java.util.Collection;
import org.geoint.acetate.domain.annotation.Accessor;
import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.domain.annotation.MultiComposite;

/**
 * A data model type which is comprised of zero or more objects.
 *
 * @param <T> java object type used to represent this model
 */
@Model(name = "compositeModel", displayName = "Composite Model",
        domain = DataModel.ACETATE_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public interface CompositeModel<T> extends DataModel<T> {

    /**
     * Returns all the component models contained by this composite.
     *
     * @return all models contained by this composite
     */
    @Accessor
    @MultiComposite(name = "composites", itemName = "composite")
    Collection<CompositeComponentModel<?, ?>> getCompositeModels();

}
