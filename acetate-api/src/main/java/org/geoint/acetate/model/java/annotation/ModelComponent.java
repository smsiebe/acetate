package org.geoint.acetate.model.java.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.DataConstraint;
import org.geoint.acetate.model.DataType;
import org.geoint.acetate.transform.BinaryCodec;

/**
 * Used within {@link Model} to modify the data model through annotations.
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelComponent {

    /**
     * Relative path of the model component to change.
     *
     * @return relative component path
     */
    String path();

    /**
     * Optional listing of model component aliases.
     *
     * @return optional aliases, empty array means no aliases.
     */
    String[] alias() default {};

    /**
     * Optional explicity defined DataType.
     *
     * @return optional explicit data type assignment, class of DataType means
     * to use the default means of resolving a DataType
     */
    Class<? extends DataType> type() default DataType.class;

    /**
     * Optional data constraints added to the model component.
     *
     * @return data constraints for this model
     */
    Class<? extends DataConstraint>[] constraints() default {};

    /**
     * Optional explicitly defined chain of binary converter for this model
     * component.
     *
     *
     * @return optional binary converter chain
     */
    Class<? extends BinaryCodec>[] codec() default {};

}
