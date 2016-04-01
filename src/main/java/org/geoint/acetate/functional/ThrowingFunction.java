/*
 * Copyright 2016 geoint.org.
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
package org.geoint.acetate.functional;

import java.util.function.Function;

/**
 * Represents a function that accepts one argument, ideally produces a result,
 * or throws a Throwable exception if the function fails to produce a result
 * (pre-conditions not met, external dependencies not met, whatever your
 * pleasure).
 *
 * TODO implement chaining default methods like {@link Function}.
 *
 * @author steve_siebert
 * @param <T> type to be transformed
 * @param <R> type returned
 * @param <X> exception type that may be thrown
 */
@FunctionalInterface
public interface ThrowingFunction<T, R, X extends Throwable> {

    public R apply(T t) throws X;

}
