/*
 * Copyright 2015 Expression project.organization is undefined on line 4, column 57 in Templates/Licenses/license-apache20.txt..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geoint.acetate.domain.event;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.domain.provider.metamodel.DomainMetaModel;
import org.geoint.metamodel.MetaAttribute;
import org.geoint.metamodel.MetaModel;

/**
 * Identifies the type as a domain model event.
 *
 * @author steve_siebert
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MetaModel
public @interface Event {

    /**
     * OPTIONAL Name of the domain that defines this model.
     * <p>
     * If not explicitly set a domain type will inherit the domain name defined
     * on the package (if set) or will use the default domain name.
     *
     * @return domain model name
     */
    @MetaAttribute(name = DomainMetaModel.DOMAIN_NAME)
    String domain() default "";

    /**
     * OPTIONAL Domain model version of the data type.
     * <p>
     * If not explicitly set a domain type will inherit the version defined on
     * the package (if set) or will use the default domain version.
     *
     * @return
     */
    @MetaAttribute(name = DomainMetaModel.DOMAIN_VERSION)
    String domainVersion() default "";

    /**
     * OPTIONAL Domain type name.
     * <p>
     * Setting this attribute at the package level will have no effect.
     *
     * @return domain type name
     */
    @MetaAttribute(name = DomainMetaModel.DOMAIN_TYPE_NAME)
    String name() default "";

}
