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
package org.geoint.acetate.java.model;

import java.util.Optional;
import java.util.Set;
import org.geoint.acetate.model.InvalidModelException;
import org.geoint.acetate.model.ResourceModel;

/**
 * Domain resource model represented by a java class.
 *
 * @author steve_siebert
 * @param <T> java class representing the domain resource
 */
public interface ResourceClass<T> extends TypeClass<T>, ResourceModel {

    @Override
    Set<OperationMethodModel<T,?>> getOperations()
            throws InvalidModelException;

    @Override
    Optional<OperationMethodModel<T,?>> findOperation(String operationName)
            throws InvalidModelException;

    @Override
    Set<TypeClassRef<ValueClass<?>>> getAttributes()
            throws InvalidModelException;

    @Override
    Optional<TypeClassRef<ValueClass<?>>> findAttribute(String name)
            throws InvalidModelException;

    @Override
    Set<TypeClassRef<ResourceClass<?>>> getLinks()
            throws InvalidModelException;

    @Override
    Optional<TypeClassRef<ResourceClass<?>>> findLink(String linkName)
            throws InvalidModelException;
}
