package org.geoint.acetate.model;

import java.util.Collection;
import org.geoint.acetate.data.transform.ObjectCodec;
import org.geoint.acetate.model.attribute.Attributable;
import org.geoint.acetate.model.constraint.Constrainable;

/**
 * A supported data object within a domain model.
 *
 * @param <T> associated java data type
 */
public interface ObjectModel<T> extends ModelComponent,
        Attributable, Constrainable {

    /**
     * Component behavior/operations.
     *
     * @return component operations or empty collection if no behavior is found
     * on the component
     */
    Collection<OperationModel> getOperations();

    /**
     * Objects which make up (compose) this object.
     *
     * @return child (composite) objects
     */
    Collection<ContextualComponent> getComposites();

    /**
     * Codec used to encode/decode this component as to/from bytes.
     *
     * @return component codec
     */
    ObjectCodec<T> getCodec();
}
