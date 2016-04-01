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
package org.geoint.acetate;

import java.util.function.BiConsumer;
import org.geoint.acetate.model.NamedMapRef;
import org.geoint.acetate.model.NamedRef;
import org.geoint.acetate.model.NamedTypeRef;

/**
 * Provides model driven interface to access domain instance(s) modeled as
 * key/value pairs that compose or are are related to a domain instance.
 *
 * @author steve_siebert
 */
public interface MapInstanceRef extends NamedInstanceRef<NamedMapRef> {

    public default NamedTypeRef getKeyModel() {
        return getModel().getKeyRef();
    }

    public default NamedRef getValueModel() {
        return getModel().getValueRef();
    }

    TypeInstanceRef getKey();

    NamedInstanceRef getValue();

    void forEachType(BiConsumer<TypeInstanceRef, NamedInstanceRef> instance);
}
