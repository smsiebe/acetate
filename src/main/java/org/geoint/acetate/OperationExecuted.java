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
import java.util.Objects;
import org.geoint.acetate.model.TypeDescriptor;

/**
 * Abstract result from the execution of an operation.
 *
 * @see OperationCompleted
 * @see OperationFailed
 */
public abstract class OperationExecuted {

    private final TypeDescriptor resourceDescriptor;
    private final String resourceGuid;
    private final String resourceVersion;
    private final String operationName;
    private final Instant executionTime;
    private final Duration executionDuration;

    public OperationExecuted(TypeDescriptor resourceDescriptor,
            String resourceGuid, String resourceVersion, String operationName,
            Instant executionTime, Duration executionDuration) {
        this.resourceDescriptor = resourceDescriptor;
        this.resourceGuid = resourceGuid;
        this.resourceVersion = resourceVersion;
        this.operationName = operationName;
        this.executionTime = executionTime;
        this.executionDuration = executionDuration;
    }

    /**
     * Domain descriptor of the subject resource.
     *
     * @return resource descriptor
     */
    public TypeDescriptor getResourceDescriptor() {
        return this.resourceDescriptor;
    }

    /**
     * Unique identifier of the related resource instance.
     *
     * @return resource id
     */
    public String getResourceGuid() {
        return this.resourceGuid;
    }

    /**
     * Version of the resource instance <b>before</b> the operation was
     * executed.
     *
     * @return resource instance version
     */
    public String getResourceVersion() {
        return this.resourceVersion;
    }

    /**
     * Name of the operation executed.
     *
     * @return operation name
     */
    public String getOperationName() {
        return this.operationName;
    }

    /**
     * Local start time of the machine which executed the operation.
     *
     * @return execution start time
     */
    public Instant getExecutionTime() {
        return this.executionTime;
    }

    /**
     * The duration of the execution time.
     *
     * @return execution duration
     */
    public Duration getExecutionDuration() {
        return this.executionDuration;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.resourceDescriptor);
        hash = 17 * hash + Objects.hashCode(this.resourceGuid);
        hash = 17 * hash + Objects.hashCode(this.resourceVersion);
        hash = 17 * hash + Objects.hashCode(this.executionTime);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OperationExecuted other = (OperationExecuted) obj;
        if (!Objects.equals(this.resourceGuid, other.resourceGuid)) {
            return false;
        }
        if (!Objects.equals(this.resourceVersion, other.resourceVersion)) {
            return false;
        }
        if (!Objects.equals(this.resourceDescriptor, other.resourceDescriptor)) {
            return false;
        }
        if (!Objects.equals(this.executionTime, other.executionTime)) {
            return false;
        }
        return true;
    }

}
