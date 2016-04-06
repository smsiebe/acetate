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
import org.geoint.acetate.model.TypeDescriptor;

/**
 * Returned on successful execution of an operation.
 */
public final class OperationCompleted extends OperationExecuted {

    private final EventInstance event;

    public OperationCompleted(TypeDescriptor resourceDescriptor,
            String resourceGuid, String resourceVersion, String operationName,
            Instant executionTime, Duration executionDuration,
            EventInstance event) {
        super(resourceDescriptor, resourceGuid, resourceVersion, operationName,
                executionTime, executionDuration);
        this.event = event;
    }

    public static OperationCompleted newInstance(OperationInstance operation,
            ResourceInstance resource, Instant executionTime, Duration duration,
            EventInstance result) {
        return new OperationCompleted(resource.getTypeDescriptor(),
                resource.getResourceGuid(),
                resource.getResourceVersion(),
                operation.getName(),
                executionTime,
                duration,
                result);
    }

    /**
     * Domain event instance returned by the operation.
     *
     * @return operation result
     */
    public EventInstance getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return String.format("Operation '%s' on resource '%s-%s' of type '%s' "
                + "completed successfully.",
                getOperationName(),
                getResourceGuid(), getResourceVersion(),
                getResourceDescriptor().toString());
    }
}
