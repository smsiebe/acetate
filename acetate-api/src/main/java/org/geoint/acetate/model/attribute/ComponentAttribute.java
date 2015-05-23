package org.geoint.acetate.model.attribute;

import org.geoint.acetate.model.InheritableComponent;

/**
 * Marker interface defining an attribute of a data model component.
 *
 * Acetate uses data attributes to store model-relevant characteristics of a
 * model component. External frameworks using acetate, application code, or
 * otherwise is encouraged to use model component attributes to help track meta
 * properties of the model, however, there are some special aspects that should
 * be considered before manipulating these attributes:
 * <br/>
 * <ul>
 * <li>The <i>org.geoint.acetate</i> package namespace is reserved for the
 * acetate framework. External applications must not register new attributes or
 * attempt to manipulate any attributes using this namespace directly through
 * the attribute API.</li>
 * <li>Like the rest of the data model, once built a data model cannot be
 * changed (is immutable), which includes component attributes. All attributes
 * that are added must also be immutable.</li>
 * </ul>
 *
 */
public interface ComponentAttribute extends InheritableComponent {

}
