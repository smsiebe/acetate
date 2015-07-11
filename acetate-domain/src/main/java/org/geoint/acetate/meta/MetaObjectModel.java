package org.geoint.acetate.meta;

import java.util.Collection;
import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.domain.model.DomainModel;
import org.geoint.acetate.meta.annotation.MetaOperation;

/**
 * 
 * @see MetaObject
 */
@Model(name = "Object Model",
        domainName = DomainModel.ACETATE_DOMAIN_NAME,
        domainVersion = DomainModel.ACETATE_DOMAIN_VERSION)
public interface MetaObjectModel {

    String getDomainName();
    
    MetaVersion getDomainVersion();
    
    Collection<MetaOperation> getRequiredOperations();
    
}
