package org.geoint.acetate.model.address;

import org.geoint.acetate.model.CompositeComponent;

/**
 * Address for a {@link CompositeComponent}.
 *
 * A CompositeAddress is used to address DomainCompositeObjects,
 * DomainOperations, and DomainAggregateObject models.
 *
 * @see OperationAddress
 */
public interface CompositeAddress extends ComponentAddress, ObjectAddress {

    /**
     * Local name of the composite object.
     *
     * @return container-unique name of the composite component
     */
    String getLocalName();

}
