/*
 * Copyright 2015 geoint.org.
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
package org.geoint.acetate.domain.operation;

import java.time.ZonedDateTime;
import org.geoint.acetate.domain.data.DomainTypeData;
import org.geoint.acetate.domain.event.Event;

/**
 * Wraps the response of an entity operation.
 *
 * @author steve_siebert
 */
@Event(name = "OperationResponse")
public interface OperationResponse {

    ZonedDateTime getResponseTime();

    /**
     * Client session identifier.
     *
     * @return client session identifier
     */
    String getClientSessionId();

    /**
     * The client-provided identifier for the request, which must be provided
     * back to the client in the OperationResponse.
     *
     * Application code must not use this ID for any purpose, it's strictly used
     * to aid the client-side in managing asynchronous request/response.
     *
     * @return client-provided request id
     */
    String getClientRequestId();

    /**
     * Operation response.
     *
     * @return operation response
     */
    DomainTypeData<?> getResponse();
}
