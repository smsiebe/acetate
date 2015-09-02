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
package org.geoint.acetate;

import org.geoint.acetate.data.Data;
import org.geoint.acetate.data.DomainTypeData;

/**
 * A domain behavior which converts a domain type (supported data format) to a
 * domain type/supported data format.
 * <p>
 * Executing a domain conversion behavior is idempotent and safe and does not
 * change the value of the source domain type data.
 *
 * @author steve_siebert
 * @param <F> type converting from
 * @param <T> type converting to (the result of the operation)
 * @see Data
 */
public interface DomainConverter<F, T> extends DomainBehavior<DomainType<F>> {

    /**
     * Returns an array always containing a single type model for the source
     * data type of the conversion.
     *
     * @return source model
     */
    @Override
    public DomainType<?>[] getParameterTypes();

    /**
     * Synchronously execute the behavior.
     *
     * @param source
     * @return returns the result of executing the behavior
     */
    DomainTypeData<T> execute(DomainTypeData<F> source);

}
