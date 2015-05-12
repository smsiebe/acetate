package org.geoint.acetate.model;

import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.model.attribute.Attributable;
import org.geoint.acetate.model.constraint.Constrainable;

/**
 * The component context contains contextually defined traits of a component
 * which may vary depending on where the component is used within a domain
 * model.
 *
 * A component may have default contextual traits which may be overridden
 * depending on the context of the component, defining how acetate (or other
 * frameworks) operate on the component in its context. For example, it is
 * possible to define traits that directly impact the way acetate binds data to
 * the model. The context of a component can be defined based on its use within
 * the model (for example when a component is used as a composite within another
 * component) or potentially based on some runtime-defined context.
 *
 * @param <T>
 */
public interface ComponentContext<T> extends Attributable, Constrainable {

    /**
     * Codec used to encode/decode this component as to/from bytes.
     *
     * @return component codec
     */
    ObjectCodec<T> getCodec();
}
