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
package org.geoint.acetate.util;

/**
 * Thrown if the generated key is equal to an existing item within a KeyedSet.
 *
 * @author steve_siebert
 */
public class DuplicatedKeyedItemException extends Exception {

    public DuplicatedKeyedItemException() {
    }

    public DuplicatedKeyedItemException(String message) {
        super(message);
    }

    public DuplicatedKeyedItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedKeyedItemException(Throwable cause) {
        super(cause);
    }

}
