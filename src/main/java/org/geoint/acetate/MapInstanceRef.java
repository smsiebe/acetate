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

import org.geoint.acetate.model.NamedMapRef;

/**
 * Map instance reference.
 *
 * @author steve_siebert
 */
public class MapInstanceRef implements InstanceRef<NamedMapRef> {

    private final NamedMapRef refModel;
    private final TypeInstanceRef key;
    private final InstanceRef value;

    protected MapInstanceRef(NamedMapRef refModel,
            TypeInstanceRef key, InstanceRef value) {
        this.refModel = refModel;
        this.key = key;
        this.value = value;
    }

    public static MapInstanceRef newInstance(NamedMapRef refModel,
            TypeInstanceRef key,
            InstanceRef value) {
        return new MapInstanceRef(refModel, key, value);
    }

    public String getKeyName() {
        return getKeyRef().getName();
    }

    public TypeInstanceRef getKeyRef() {
        return key;
    }

    public InstanceRef getValueRef() {
        return value;
    }

    @Override
    public NamedMapRef getRefModel() {
        return refModel;
    }

    public String getValueName() {
        return getValueRef().getName();
    }

}
