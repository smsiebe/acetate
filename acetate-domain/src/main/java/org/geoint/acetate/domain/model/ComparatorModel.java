package org.geoint.acetate.domain.model;

import java.util.Comparator;
import org.geoint.acetate.domain.annotation.Accessor;
import org.geoint.acetate.domain.annotation.Entity;
import org.geoint.acetate.domain.annotation.Query;

/**
 * Service which compares two instances of data.
 * <p>
 * Domain model Comparators must be annotated with the {@link Service}
 * annotation to be auto-registered with the domain model.
 *
 * @param <T>
 * @see Compare
 * @see DataTypeModel
 */
@Entity(name = "comparatorModel", displayName = "Comparator Model",
        domain = DataModel.ACETATE_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public interface ComparatorModel<T> extends DataModel<T> {

    @Query
    Comparator<T> getComparator();

    /**
     * The data model this codec can be applied.
     *
     * @return supported data model type
     */
    @Accessor
    DataModel<T> getDataModel();
}
