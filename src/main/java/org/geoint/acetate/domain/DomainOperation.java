package org.geoint.acetate.domain;

/**
 * Entity behavior defined by the domain model.
 */
public interface DomainOperation {

    DomainEvent execute(DomainEntity entity, DomainType... parameters);

}
