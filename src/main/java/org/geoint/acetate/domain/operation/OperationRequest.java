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

import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.geoint.acetate.data.filter.Unfiltered;
import org.geoint.acetate.domain.data.DomainTypeData;
import org.geoint.acetate.domain.event.Event;

/**
 * An OperationRequest is an event that describes the semantics of the requested
 * operation.
 *
 * @author steve_siebert
 */
@Event(name = "OperationRequest")
public interface OperationRequest {

    Optional<InetAddress> getRequestorIP();

    ZonedDateTime getRequestTime();

    /**
     * Client session identifier.
     *
     * @return client session identifier
     */
    String getClientSessionId();

    /**
     * The client-provided identifier for the request, which must be provided
     * back to the client in the OperationResponse.
     * <p>
     * Client request identifiers <b>should</b> be session-unique, but
     * application must not rely on this. Application code must not use this ID
     * for any purpose other than to route operation responses back to the
     * requesting client.
     * <p>
     * <b>NOTE</b> that the client request ID is unfiltered.
     *
     * @return client-provided request id
     */
    @Unfiltered
    byte[] getClientRequestId();

    /**
     * Domain name of the target entity for this operation.
     *
     * @return entity domain name
     */
    String getEntityDomain();

    /**
     * Domain version of the target entity for this operation.
     *
     * @return entity domain version
     */
    String getEntityVersion();

    /**
     * Operation name to execute.
     *
     * @return operation name
     */
    String getOperationName();

    /**
     * Operation parameters in operation-defined sequence order.
     *
     * @return operation parameters
     */
    DomainTypeData<?>[] getParameters();

}
