package org.geoint.acetate.model.attribute;

import org.geoint.acetate.model.EntityModel;
import org.geoint.acetate.model.annotation.EntityId;

/**
 * "Marker" annotation which is used to identity the globally unique identifier
 * of an {@link EntityModel}.
 *
 * All Entity Objects <b>MUST</b> specify one (and only one) GUID.
 *
 * <p>
 * A GUID is a generic term; the resultant field can be in any format so long as
 * the resultant model instance:
 *
 * <ul>
 * <li>Has only one GUID identified in each class (see GUID Inheritance
 * below).</li>
 * <li>Can be uniquely addressed/identified using this single field;
 * distinguishing it from any other model instance.</li>
 * <li>Will always reference the same model instance and is not reused on
 * different model instances (even if the model instance is deleted). Further,
 * normally state changes to the model instance do not change the GUID (unless
 * that is by design of the data model, such as an immutable domain model).</li>
 * </ul>
 *
 * <h2>GUID Formatting</h2>
 * Acetate does not place any constraints on the format of a GUID.
 * <p>
 * Applications should be aware, however, of conflicting constraints defined by
 * specifications they intend to use within their application architecture. For
 * example, the https://www.ietf.org/rfc/rfc4122.txt UUID specification (RFC
 * 4122) conflicts with the W3C Extensible Markup Language (XML) Version 1.0
 * specification for the xml:id attribute. The UUID specification allows any
 * hexadecimal digit as the first character of the UUID, while xml:id must not
 * start with a ASCII digit. Frameworks using acetate may accommodate this,
 * though this is out of scope of the acetate modeling framework.
 *
 * <h2>GUID Inheritance</h2>
 * Since a data model may only have one GUID identified, fields annotated as
 * GUID are <i>conditionally</i> inheritable. What this means is that while the
 * GUID annotation is annotated as {@link Inherited}, acetate model
 * implementations must only use the "most concrete" GUID discovered. In other
 * words, if a class extends another, and both define a GUID field, the GUID
 * field identified by the child class wins - the parent class field identified
 * as a GUID is treated as a normal field.
 *
 * @see EntityId
 */
public final class EntityIdAttribute implements ModelAttribute {

    public EntityIdAttribute() {
    }

}
