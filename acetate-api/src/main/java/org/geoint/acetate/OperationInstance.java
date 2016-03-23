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

import org.geoint.acetate.model.OperationModel;

/**
 *
 * @author steve_siebert
 * @param <R> resource instance context for this operation
 * @param <E> event created on successful execution of the operation
 */
public interface OperationInstance<R extends ResourceInstance, E extends EventInstance> {

    OperationModel getModel();
    
    /**
     * Asynchronously invokes the resource operation.
     *
     * @param handler operation result callback
     * @param resource resource context of the operation
     * @param params operation parameters
     */
    void asyncInvoke(OperationResultHandler<E> handler, R resource,
            TypeInstanceRef... params);

    /**
     * Synchronously invokes the resource operation.
     * <p>
     * The operation may be executed on the calling thread, or the calling
     * thread may simply wait while the operation is executed on a different
     * thread.
     *
     * @param resource resource context of the operation
     * @param params operation parameters
     * @return operation execution result
     * @throws OperationExecutionException if the operation failed execution
     */
    E syncInvoke(R resource, TypeInstanceRef... params) 
            throws OperationExecutionException;
}
