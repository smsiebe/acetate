package org.geoint.acetate.bind.object.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.spi.model.DataType;
import org.geoint.acetate.transform.StringConverter;

/**
 * Overrides default model meta data for a model component.
 *
 */
@Documented
@Target({ElementType.TYPE, ElementType.TYPE_USE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {

    /**
     * Optional listing of model component aliases.
     *
     * @return optional aliases, empty array means no aliases.
     */
    String[] alias() default new String[0];

    /**
     * Optional explicity defined DataType.
     *
     * @return optional explicit data type assignment, class of DataType means
     * to use the default means of resolving a DataType
     */
    Class<? extends DataType> type() default DataType.class;

    /**
     * Optional explicitly defined String formatter for this model component.
     *
     * If used along with an explicitly defined DataType, this formatter wins.
     *
     * @return optional default string formatter override, class of
     * StringConverter means to use the DataType default formatter
     */
    Class<? extends StringConverter> defaultFormatter()
            default StringConverter.class;

    /**
     * Optional explicitly defined binary converter for this model component.
     *
     * If used along with an explicitly defined DataType, this converter wins.
     *
     * @return optional default binary converter override, class of
     * BinaryConverter means to use the DataType default binary converter
     */
    Class<? extends BinaryConverter> defaultConverter()
            default BinaryConverter.class;

    /**
     * Optional data constraints added to the model component.
     */
    Class<? extends DataConstraint>[] constraints() default new Class[0];

    /**
     * Model overrides for components within this model.
     */
    ModelComponent[] components() default new ModelComponent[0];
}
