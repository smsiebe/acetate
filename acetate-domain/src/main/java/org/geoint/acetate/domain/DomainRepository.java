package org.geoint.acetate.domain;

import java.util.Collection;
import org.geoint.acetate.domain.annotation.Operation;
import org.geoint.acetate.domain.annotation.Query;
import org.geoint.acetate.domain.annotation.Service;
import org.geoint.acetate.domain.model.DataModel;
import org.geoint.acetate.domain.model.Taxonomy;
import org.geoint.acetate.model.common.Version;

/**
 * Domain model repository.
 *
 */
@Service(name="domainRepository", displayName="Domain Repository",
        domain=DataModel.ACETATE_DOMAIN_NAME, 
        version=DataModel.ACETATE_DOMAIN_VERSION)
public interface DomainRepository {

    @Query
    Collection<Taxonomy> findAll();
    
    @Query
    Collection<Taxonomy> find(String domainName, Version version);
    
    @Operation
    DomainRegistered register (Taxonomy domain);
    
    @Operation
    DomainDeprecated deprecate(Taxonomy domain);
    
}
