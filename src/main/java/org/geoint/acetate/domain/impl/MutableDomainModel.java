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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.geoint.acetate.domain.DomainComponent;
import org.geoint.acetate.domain.entity.DomainEntity;
import org.geoint.acetate.domain.event.DomainEvent;
import org.geoint.acetate.domain.DomainModel;
import org.geoint.acetate.domain.DomainType;
import org.geoint.acetate.domain.IllegalModelException;
import org.geoint.acetate.domain.DomainUtil;

/**
 *
 * @author steve_siebert
 */
public class MutableDomainModel implements DomainModel {

    private final String domainName;
    private final String domainVersion;
    private final Map<String, DomainType> types;

    public MutableDomainModel(String domainName, String domainVersion) {
        this.domainName = domainName;
        this.domainVersion = domainVersion;
        this.types = new HashMap<>();
    }

    public MutableDomainModel(DomainModel dm) throws IllegalModelException {
        this(dm.getDomainName(), dm.getDomainVersion());
        this.mergeDomain(dm);
    }

    protected void setType(DomainType t) {
        types.put(t.getName(), t);
    }

    @Override
    public Collection<DomainType> getTypes() {
        return types.values();
    }

    @Override
    public boolean hasType(String typeName) {
        return types.containsKey(typeName);
    }

    @Override
    public Optional<DomainType> getType(String typeName) {
        return Optional.ofNullable(types.get(typeName));
    }

    public void merge(DomainModel md) throws IllegalModelException {
        this.mergeDomain(md);
    }

    @Override
    public String getDomainName() {
        return domainName;
    }

    @Override
    public String getDomainVersion() {
        return domainVersion;
    }

    public boolean isSameDomain(DomainComponent domainModel) {
        return this.getDomainName().equalsIgnoreCase(domainModel.getDomainName())
                && this.getDomainVersion().equalsIgnoreCase(domainModel.getDomainVersion());
    }

    @Override
    public Collection<DomainEntity> getEntityTypes() {
        return getTypes().stream()
                .filter(DomainUtil::isEntity)
                .map((t) -> (DomainEntity) t)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<DomainEvent> getEventTypes() {
        return getTypes().stream()
                .filter(DomainUtil::isEvent)
                .map((t) -> (DomainEvent) t)
                .collect(Collectors.toList());
    }

    private void mergeDomain(DomainModel md) throws IllegalModelException {
        //merge types
        for (DomainType mt : md.getTypes()) {
            final String typeName = mt.getName();

            if (!this.hasType(typeName)) {
                this.setType(mt);
                continue;
            }

            //domain type name collision
            DomainType dt = this.getType(typeName).get();

            if ((DomainUtil.isEntity(dt) && DomainUtil.isEvent(mt))
                    || DomainUtil.isEntity(mt) && DomainUtil.isEvent(dt)) {
                //domain type can't both be an Entity and an Event
                throw new IllegalModelException(this.getDomainName(),
                        this.getDomainVersion(),
                        String.format("Domain type '%s' cannot both be "
                                + "an Entity and Event", dt.toString())
                );
            }

            Class<?> mc = mt.getTypeClass();
            Class<?> dc = dt.getTypeClass();

            if (dc.equals(mc)) {
                //same class, same domain model type, no worries
                continue;
            } else if (mc.isAssignableFrom(dc)) {
                //dt extends mt
                this.setType(dt);
                continue;
            } else if (dc.isAssignableFrom(mc)) {
                //mt extends dt
                this.setType(mt);
                continue;
            } else {
                //same type, different classes
                throw new IllegalModelException(this.getDomainName(),
                        this.getDomainVersion(), String.format(
                                "Two concrete classes found representing domain "
                                + "type '%s': ['%s', '%s']",
                                dt.getName(),
                                mc.getCanonicalName(),
                                dc.getCanonicalName())
                );
            }

        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.domainName);
        hash = 53 * hash + Objects.hashCode(this.domainVersion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MutableDomainModel other = (MutableDomainModel) obj;
        if (!Objects.equals(this.domainName, other.domainName)) {
            return false;
        }
        if (!Objects.equals(this.domainVersion, other.domainVersion)) {
            return false;
        }
        return true;
    }

}
