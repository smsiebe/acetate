package org.geoint.acetate.domain.operation;

import org.geoint.acetate.domain.DomainComponent;
import org.geoint.acetate.domain.entity.DomainEntity;
import org.geoint.acetate.domain.DomainType;
import org.geoint.acetate.annotation.Domain;
import org.geoint.acetate.domain.event.DomainEvent;

/**
 * Entity behavior defined by the domain model.
 *
 * @param <E> java class type of the entity related to the operation
 *
 */
@Domain(name = "Operation")
public interface DomainOperation<E> extends DomainComponent {

    String getName();

    DomainEntity<E> getEntity();

    DomainType<?>[] getParameterTypes();

    DomainType<?> getReturnType();

    DomainEvent<?, E> execute(E entity, Object... parameters);

}
