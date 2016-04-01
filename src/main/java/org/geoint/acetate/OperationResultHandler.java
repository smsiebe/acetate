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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Operation result callback.
 *
 * @see ResourceInstanceOperation#asyncInvoke()
 * @author steve_siebert
 */
@FunctionalInterface
public interface OperationResultHandler {

    /**
     * Called on successful completion of the operation.
     *
     * @param event results from the operation
     */
    void onSuccess(EventInstance event);

    /**
     * Called when an operation throws an exception (fails to complete
     * successfully).
     *
     * @param ex wrapped exception thrown by the operation
     */
    default void onFail(OperationExecutionException ex) {
        Logger.getLogger(OperationResultHandler.class.getName()).log(
                Level.WARNING, "Operation execution failed.", ex);
    }
}
