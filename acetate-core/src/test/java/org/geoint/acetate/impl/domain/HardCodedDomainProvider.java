package org.geoint.acetate.impl.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.domain.model.DomainModel;
import org.geoint.acetate.domain.model.ExceptionModel;
import org.geoint.acetate.domain.model.ObjectModel;
import org.geoint.acetate.domain.model.Ontology;
import org.geoint.acetate.domain.model.OperationModel;
import org.geoint.acetate.domain.model.ParameterModel;
import org.geoint.acetate.domain.model.ReturnModel;
import org.geoint.acetate.impl.model.reflect.ReflectionModeler;
import org.geoint.acetate.meta.MetaVersion;
import org.geoint.acetate.meta.ModelException;
import org.geoint.acetate.meta.VersionQualifier;
import org.geoint.acetate.spi.MetaProvider;

/**
 * Test MetaProvider which is simply hard-coded with the acetate metamodel
 * domain classes.
 */
public class HardCodedDomainProvider implements MetaProvider {

    private static final Logger logger
            = Logger.getLogger(HardCodedDomainProvider.class.getName());
    public static final Class<?>[] ACETATE_DOMAIN_CLASSES
            = {Ontology.class,
                DomainModel.class,
                ObjectModel.class,
                OperationModel.class,
                ParameterModel.class,
                ReturnModel.class,
                ExceptionModel.class,
                MetaVersion.class,
                VersionQualifier.class};

    private static final Collection<ObjectModel> acetateDomainObjects
            = acetateDomainModel();

    @Override
    public Collection<ObjectModel> findAll() {
        return acetateDomainObjects;
    }

    @Override
    public Collection<ObjectModel> find(
            String domainName, MetaVersion domainVersion) {
        if (domainName.equalsIgnoreCase(DomainModel.ACETATE_DOMAIN_NAME)
                && domainVersion.asString().contentEquals(DomainModel.ACETATE_DOMAIN_VERSION)) {
            return findAll();
        }
        return Collections.EMPTY_LIST;
    }

    private static Collection<ObjectModel> acetateDomainModel() {
        try {
            return ReflectionModeler.model(
                    ACETATE_DOMAIN_CLASSES
            );
        } catch (ModelException ex) {
            //we shouldn't get here
            logger.log(Level.SEVERE, "Unable to load acetate data model.");
        }
        return Collections.EMPTY_LIST;
    }
}
