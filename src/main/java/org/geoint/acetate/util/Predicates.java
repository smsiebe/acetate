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
package org.geoint.acetate.util;

import java.util.function.Predicate;

/**
 * Utility classes to work with {@link Predicate} functional methods and method
 * references.
 *
 * @author steve_siebert
 */
public class Predicates {

    /**
     * Returns the Predicate functional method.
     * <p>
     * This method is useful to receive the Predicate for a method reference:
     * {@code Predicate<Object> nonNull = Predicates.of(Objects::nonNull); }
     *
     * @param <T>
     * @param p predicate to return
     * @return predicate
     */
    public static <T> Predicate<T> of(Predicate<T> p) {
        return p;
    }

    /**
     * Returns the negated Predicate of the one provided.
     * <p>
     * This method is functionally similar to {@link Predicate#negate() } and is
     * useful to negate a method reference:
     * {@code Predicate<String> notEmpty = Predicates.negate(String::isEmpty);}
     *
     * @param <T>
     * @param p predicate to negate
     * @return negated predicate
     */
    public static <T> Predicate<T> negate(Predicate<T> p) {
        return p.negate();
    }
}
