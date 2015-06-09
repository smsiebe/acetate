package gov.ic.geoint.acetate.bind;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides creational context on what type, and how, to create an instance.
 */
@Documented
@Target({
    ElementType.TYPE,
    ElementType.TYPE_PARAMETER,
    ElementType.TYPE_USE,
    ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Factory {

    /**
     * Optional - Explictly defines the class to instantiate, otherwise the
     * factory uses the {@link CreationContext context} to create the instance.
     *
     * @return class of object type to create
     */
    Class<?> type() default Object.class;

    /**
     * Optional - Data factory to use to instantiate the ComponentAttribute
     * instance.
     *
     * If not specified, the {@link NoArgDataFactory} is used.
     *
     * @return data factory to use to construct the ComponentAttribute instance
     */
    Class<? extends DataFactory> factory() default NoArgDataFactory.class;
}
