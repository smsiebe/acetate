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

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import org.geoint.acetate.model.InvalidModelException;
import org.geoint.acetate.model.ResourceOperation;
import org.geoint.acetate.model.ResourceType;

/**
 * Invokable resource operation.
 *
 * @author steve_siebert
 */
public class OperationInstance {

    protected final ResourceInstance resource;
    protected final ResourceOperation model;

    protected OperationInstance(ResourceInstance resource,
            ResourceOperation model) {
        this.model = model;
        this.resource = resource;
    }

    public static OperationInstance newInstance(ResourceInstance instance,
            String operationName) throws InvalidModelException {

        ResourceType rModel = instance.getModel();
        ResourceOperation oModel = rModel.findOperation(operationName)
                .orElseThrow(() -> new InvalidModelException(String.format(
                        "Unable to create instance of operation '%s', no such "
                        + "operation is defined for resource type '%s'",
                        operationName,
                        rModel.getTypeDescriptor().toString())));

        return new OperationInstance(instance, oModel);

    }

    public ResourceOperation getModel() {
        return model;
    }

    public String getName() {
        return getModel().getName();
    }

    public Optional<String> getDescription() {
        return getModel().getDescription();
    }

    /**
     * Synchronously invokes the resource operation.
     * <p>
     * The operation may be executed on the calling thread, or the calling
     * thread may simply wait while the operation is executed on a different
     * thread.
     *
     * @param params operation parameters
     * @return operation execution result
     */
    public OperationExecuted invoke(InstanceRef... params) {
        Instant executionTime = Instant.now();
        try {
            EventInstance event = model.getFunction().apply(resource, params);
            return OperationCompleted.newInstance(this, resource,
                    executionTime,
                    Duration.between(executionTime, Instant.now()),
                    event);
        } catch (Throwable ex) {
            return OperationFailed.newInstance(this, resource,
                    executionTime,
                    Duration.between(executionTime, Instant.now()),
                    ex);
        }
    }
}
