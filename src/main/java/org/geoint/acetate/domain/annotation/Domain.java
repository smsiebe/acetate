package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.metamodel.MetaModel;

/**
 * Annotation used to declare a basic domain type as part of a domain model.
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MetaModel
public @interface Domain {

    /**
     * Name of the domain that defines this model.
     *
     * @return domain model name
     */
    String domain();

    /**
     * Domain model version of the data type.
     *
     * @return 
     */
    String domainVersion();
    
    /**
     * OPTIONAL Domain type name.
     * 
     * @return domain type name
     */
    String name();
    
   

}
