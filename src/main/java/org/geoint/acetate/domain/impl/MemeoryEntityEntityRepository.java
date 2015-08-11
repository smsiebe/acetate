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
package org.geoint.acetate.domain.impl;

import java.util.Collection;
import org.geoint.acetate.domain.entity.DomainEntity;
import org.geoint.acetate.domain.entity.Entity;
import org.geoint.acetate.domain.entity.EntityArchived;
import org.geoint.acetate.domain.entity.EntityBuilder;
import org.geoint.acetate.domain.entity.EntityDeleted;
import org.geoint.acetate.domain.entity.EntityException;
import org.geoint.acetate.domain.entity.EntityPersisted;
import org.geoint.acetate.domain.entity.EntityQuery;
import org.geoint.acetate.domain.entity.EntityRepository;

/**
 * In-memory repository of entities.
 *
 * @author steve_siebert
 */
public class MemeoryEntityEntityRepository implements EntityRepository<Entity> {

    @Override
    public EntityPersisted<Entity> persist(DomainEntity<Entity> toPersist)
            throws EntityException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityArchived<Entity> archive(DomainEntity<Entity> toArchive)
            throws EntityException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityDeleted<Entity> delete(DomainEntity<Entity> toDelete)
            throws EntityException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<DomainEntity<Entity>> find(EntityQuery<Entity> query)
            throws EntityException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityBuilder<Entity> builder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
