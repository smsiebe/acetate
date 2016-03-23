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

/**
 * Thrown if an operation failed to execute.
 *
 * @author steve_siebert
 */
public class OperationExecutionException extends RuntimeException {

    public OperationExecutionException(OperationInstance operation) {
        super(defaultMessage(operation));
    }

    public OperationExecutionException(String message) {
        super(message);
    }

    public OperationExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationExecutionException(OperationInstance operation,
            Throwable cause) {
        super(defaultMessage(operation), cause);
    }

    private static String defaultMessage(OperationInstance op) {
        return String.format("Operation '%s' on resource '%s.%s-%s' failed to "
                + "execute successfully.",
                op.getModel().getName(),
                op.getModel().getResourceNamespace(),
                op.getModel().getResourceType(),
                op.getModel().getResourceVersion());
    }
}
