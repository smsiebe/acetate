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
 * Returned when an operation fails to complete successfully.
 */
public final class OperationFailed extends OperationExecuted {

    private final Throwable exception;

    public OperationFailed(TypeDescriptor resourceDescriptor,
            String resourceGuid, String resourceVersion, String operationName,
            Instant executionTime, Duration executionDuration,
            Throwable exception) {
        super(resourceDescriptor, resourceGuid, resourceVersion, operationName,
                executionTime, executionDuration);
        this.exception = exception;
    }

    public static OperationFailed newInstance(OperationInstance operation,
            ResourceInstance resource, Instant executionTime, Duration duration,
            Throwable exception) {
        return new OperationFailed(resource.getTypeDescriptor(),
                resource.getResourceGuid(),
                resource.getResourceVersion(),
                operation.getName(),
                executionTime,
                duration,
                exception);
    }

    /**
     * The exception thrown by the operation or the execution environment
     * causing the operation to fail to complete.
     *
     * @return operation exception
     */
    public Throwable getException() {
        return this.exception;
    }

    @Override
    public String toString() {
        return String.format("Operation '%s' failed to complete on "
                + "resource instance '%s-%s' of type '%s'; exception type '%s' "
                + "was thrown.",
                getOperationName(),
                getResourceGuid(), getResourceVersion(),
                getResourceDescriptor().toString(),
                exception.getClass().getName());
    }
}
