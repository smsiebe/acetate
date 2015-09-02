/*
 * Copyright 2015 geoint.org.
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
package org.geoint.acetate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.metamodel.DomainMetaModel;
import org.geoint.metamodel.annotation.Model;
import org.geoint.metamodel.annotation.ModelAttribute;

/**
 * Optional explicit domain name for the java type/method.
 *
 * @author steve_siebert
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Model
public @interface DomainName {

    /**
     * Domain type name.
     * <p>
     * Setting this attribute at the package level will have no effect.
     *
     * @return domain type name
     */
    @ModelAttribute(name = DomainMetaModel.DOMAIN_TYPE_NAME)
    String value() default "";
}
