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
package org.geoint.acetate.domain.reflect;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.geoint.acetate.DomainComponent;
import org.geoint.acetate.DomainModel;
import org.geoint.acetate.DomainType;
import org.geoint.acetate.IllegalModelException;
import org.geoint.acetate.Ontology;
import org.geoint.acetate.util.Predicates;

/**
 *
 * @author steve_siebert
 */
public class MutableOntology extends MutableDomainModel
        implements Ontology {

    private final Set<MutableDomainModel> imported;
    private final Set<MutableDomainModel> registry;

    public MutableOntology(String domainName,
            String domainVersion, Set<MutableDomainModel> registry) {
        super(domainName, domainVersion);
        this.imported = new HashSet<>();
        this.registry = registry;
    }

    public MutableOntology(Ontology on, Set<MutableDomainModel> models)
            throws IllegalModelException {
        this(on.getDomainName(),
                on.getDomainVersion(), models);
        mergeOntology(on);
    }

    @Override
    protected void setType(DomainType t) {
        super.setType(t);
        //import domain model, if necessary
        importDomain(t);
    }

    private void importDomain(DomainComponent dc) {
        if (!isSameDomain(dc)) {
            if (imported.stream().anyMatch((i) -> i.isSameDomain(dc))) {
                //already imported
                return;
            }

            Optional<MutableDomainModel> importedDomain = registry.stream()
                    .filter((m) -> m.isSameDomain(dc))
                    .findFirst();

            if (importedDomain.isPresent()) {
                this.imported.add(importedDomain.get());
            } else {
                //not available in registry yet, create it
                MutableDomainModel newMergable = new MutableDomainModel(
                        dc.getDomainName(), dc.getDomainVersion());
                this.registry.add(newMergable);
                this.imported.add(newMergable);
            }
        }
    }

    private boolean isImported(DomainComponent dc) {
        return imported.stream().anyMatch((i) -> i.isSameDomain(dc));
    }

    @Override
    public void merge(DomainModel md) throws IllegalModelException {
        if (md instanceof Ontology) {
            this.mergeOntology((Ontology) md);
        } else {
            super.merge(md);
        }

    }

    @Override
    public Collection<DomainModel> getImportedDomainModels() {
        return imported.stream()
                .map((m) -> (DomainModel) m)
                .collect(Collectors.toList());
    }

    private void mergeOntology(Ontology md) throws IllegalModelException {
        //merge imports
        Ontology on = (Ontology) md;
        on.getImportedDomainModels().stream()
                .filter(Predicates.negate(this::isSameDomain))
                .filter(Predicates.negate(this::isImported))
                .forEach((dc) -> importDomain(dc));
        //merge domain types
        super.merge(md);
    }

}
