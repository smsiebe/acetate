package org.geoint.acetate.model.attribute;

/**
 * Identifies a field as the model instance version field.
 *
 * <p>
 * A root data instance <b>MUST</b> specify one (and only one) version field
 * that is unique for each state change of the model instance. The value of the
 * version field must be instance/version but the version field itself need not
 * be globally unique (think of the data instance GUID as a namespace). Data
 * instance version identifies <b>MUST NOT</b> be reused by a data instance.
 *
 * <p>
 * If a data model specifies a VersionAttribute, the field must never be null.
 *
 * <p>
 * VersionAttribute fields are inherited and only the most concrete field will
 * be used as the models version field.
 *
 * <p>
 * The data type of the VersionAttribute component must be a <i>long</i>.
 */
public final class EntityVersion implements ComponentAttribute {

    public EntityVersion() {
    }

}
