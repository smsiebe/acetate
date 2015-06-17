package org.geoint.acetate.data.transform;

import java.util.Optional;
import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.model.ModelComponent;

/**
 * Converts a non-domain model Object to a domain model object.
 *
 * Converters may be used in several different ways. One common use us to teach
 * a domain model how to handle objects it encounters which are not "known" to
 * the domain model.
 *
 * All converter implementations must be thread-safe.
 *
 * @param <F> source type (from)
 * @param <T> destination type (to)
 */
public interface Converter<F, T> {

    /**
     * Convert from a non-domain model Object to a domain model object instance.
     *
     * @param model component model
     * @param obj non-domain object instance
     * @return domain model object (converting to 'null' is acceptable)
     * @throws DataTransformException thrown if there were unexpected problems
     * converting the object
     */
    Optional<T> convert(DataModel<T> model, F obj) 
            throws DataTransformException;

    /**
     * Invert the domain model object instance back to the non-domain model
     * object type.
     *
     * @param model component model
     * @param modeled domain model component instance
     * @return reverted/inverted object or null
     * @throws DataTransformException thrown if there were unexpected problems
     * converting the object back to its original form
     */
    Optional<F> invert(DataModel<T> model, T modeled) 
            throws DataTransformException;
}
