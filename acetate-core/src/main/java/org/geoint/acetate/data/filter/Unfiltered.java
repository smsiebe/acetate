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
package org.geoint.acetate.data.filter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a piece of data that should not be, or is known to not have been,
 * {@link DataFilter filtered}.
 * <p>
 * Unfiltered user provided data should be generally be considered unsafe and
 * should only be used/requested when the developer is absolutely sure they
 * require unfiltered data values. Use cases for access to unfiltered data may
 * include password authentication, security logging, etc.
 * <p>
 * Unfiltered data creates the opportunity for exploitation of common security
 * vulnerabilities, such as buffer over-/under-flows, cross-site scripting, SQL
 * injection, etc. A piece of software itself may not be suseptible to a
 * particular vulnerability but may be used as a vehicle of exploiting the
 * vulnerability of another piece of software by allowing unfiltered to be
 * trafficked between the systems (such as is the case, for example, as web
 * applications representing data to browsers suseptible to cross-site scripting
 * attacks).
 *
 * @author steve_siebert
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Documented
public @interface Unfiltered {

}
