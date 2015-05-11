package org.geoint.acetate.model.attribute;

import gov.ic.geoint.acetate.bind.AnnotationDataFactory;
import gov.ic.geoint.acetate.bind.DataBindException;
import java.lang.annotation.Annotation;
import org.geoint.acetate.model.annotation.attribute.ComponentId;
import org.geoint.acetate.model.annotation.attribute.Version;

/**
 * Acetate standard data model attributes.
 */
public enum AcetateDataAttribute implements ComponentAttribute {

    /**
     * Identifies a data component as the globally unique identifier for the
     * data item instance. All root data instances <b>MUST</b> specify one (and
     * only one) IdAttribute; aggregates <b>SHOULD</b> have a IdAttribute.
     *
     * <p>
     * A IdAttribute is a generic term; the resultant field can be in any format
     * so long as the resultant model instance:
     *
     * <ul>
     * <li>Has only one IdAttribute identified in each class (see IdAttribute
     * Inheritance below).</li>
     * <li>Can be uniquely addressed/identified using this single field;
     * distinguishing it from any other model instance.</li>
     * <li>Will always reference the same model instance and is not reused on
     * different model instances (even if the model instance is deleted).
     * Further, normally state changes to the model instance do not change the
     * IdAttribute (unless that is by design of the data model, such as an
     * immutable domain model).</li>
     * </ul>
     *
     * <h2>IdAttribute Formatting</h2>
     * Acetate does not place any constraints on the format of a IdAttribute.
     * <p>
     * Applications should be aware, however, of conflicting constraints defined
     * by specifications they intend to use within their application
     * architecture. For example, the
     * {@link https://www.ietf.org/rfc/rfc4122.txt UUID specification (RFC 4122)}
     * conflicts with the
     * {@link W3C Extensible Markup Language (XML) Version 1.0} specification
     * for the xml:id attribute. The UUID specification allows any hexadecimal
     * digit as the first character of the UUID, while xml:id must not start
     * with a ASCII digit. Frameworks using acetate may accommodate this, though
     * this is out of scope of the acetate modeling framework.
     *
     * <h2>IdAttribute Inheritance</h2>
     * Since a data model may only have one IdAttribute identified, fields
     * annotated as IdAttribute are <i>conditionally</i> inheritable. What this
     * means is that while the IdAttribute annotation is annotated as
     * {@link Inherited}, acetate model implementations must only use the "most
     * concrete" IdAttribute discovered. In other words, if a class extends
     * another, and both define a IdAttribute field, the IdAttribute field
     * identified by the child class wins - the parent class field identified as
     * a IdAttribute is treated as a normal field.
     */
    GUID,
    /**
     * Identifies a field as the model instance version field.
     *
     * <p>
     * A root data instance <b>SHOULD</b> specify one (and only one) version
     * field that is unique for each state change of the model instance. The
     * value of the version field must be instance/version but the version field
     * itself need not be globally unique (think of the data instance GUID as a
     * namespace). Data instance version identifies <b>MUST NOT</b> be reused by
     * a data instance.
     *
     * <p>
     * If a data model specifies a VersionAttribute, the field must never be
     * null.
     *
     * <p>
     * VersionAttribute fields are inherited and only the most concrete field
     * will be used as the models version field.
     *
     * <p>
     * The data type of the VersionAttribute component must be a <i>long</i>.
     */
    VERSION;

    public static class AcetateComponentAttributeConstructor
            implements AnnotationDataFactory<ComponentAttribute> {

        /**
         * Called by the acetate model engine to retrieve an instance of the
         * component attribute.
         *
         * @param annotations
         * @return
         * @throws DataBindException
         */
        @Override
        public ComponentAttribute getInstance(Annotation... annotations)
                throws DataBindException {
            for (Annotation a : annotations) {
                if (a instanceof ComponentId) {
                    return GUID;
                } else if (a instanceof Version) {
                    return VERSION;
                }
            }
            throw new DataBindException(null,
                    "Unknown acetate component attribute.");
        }
    }
}
