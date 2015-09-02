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
package org.geoint.acetate.repository;

import java.util.Collection;
import org.geoint.acetate.DomainEntity;
import org.geoint.acetate.EntityException;
import org.geoint.acetate.annotation.Domain;

/**
 * Entity repository used to create new, find/load, and delete entity instances
 * from an application.
 *
 * @author steve_siebert
 * @param <E> entity type
 */
@Domain
public interface EntityRepository<E> {

    EntityPersisted<E> persist(DomainEntity<E> toPersist) throws EntityException;

    EntityArchived<E> archive(DomainEntity<E> toArchive) throws EntityException;

    EntityDeleted<E> delete(DomainEntity<E> toDelete) throws EntityException;

    Collection<DomainEntity<E>> find(EntityQuery<E> query)
            throws EntityException;

}
