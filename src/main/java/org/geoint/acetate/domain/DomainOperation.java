package org.geoint.acetate.domain;

/**
 * Entity behavior defined by the domain model.
 */
public interface DomainOperation {

    DomainType execute(DomainEntity entity, DomainType... parameters);

}
