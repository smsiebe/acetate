package org.geoint.acetate.impl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.attribute.Attributed;
import org.geoint.acetate.model.attribute.ModelAttribute;

/**
 * Wraps a domain model instance 
 */
public final class ImmutableDomainModel implements DomainModel {

    private final InMemoryDomainModel model;

    private ImmutableDomainModel(String name,
            long version,
            Collection<ModelComponent> components)
            throws ModelException {
        this.model = new InMemoryDomainModel(name, version);
        for (ModelComponent c : components) {
            this.model.add(c);
        }
    }

    /**
     * Return domain model(s) from a collection of domain components.
     * 
     * @param components
     * @return domain models
     */
    public static Collection<ImmutableDomainModel> fromComponents(
            Collection<ModelComponent> components) throws ModelException {
        //register "complete" domain models
            Map<String, Map<String, ModelComponent>> bucketized
                    = new HashMap<>();
            for (ModelComponent c : results.getComponents()) {

                String domainId =c.getDomainModel().getId();
                if (!bucketized.containsKey(domainId)) {
                    bucketized.put(domainId, new HashMap<>());
                }

                if (bucketized.get(domainId).containsKey(c.getName())) {
                    logger.log(Level.SEVERE,
                            "Duplicate model component ''{0}"
                            + "'' was already found in " + "domain ''{1}"
                            + "''; domain model will not be "
                            + "registered.", new Object[]{c.getName(), domainId});
                }

                bucketized.get(domainId).put(c.getName(), c);
            }

            //TODO add validation to ensure domain model is complete
            Collection<DomainModel> newModels = new ArrayList<>();
            for (Map.Entry<String, Map<String, ModelComponent>> e : bucketized.entrySet()) {
                final Collection<ModelComponent> modelComponents = e.getValue().values();
                if (modelComponents.isEmpty()) {
                    continue;
                }
                final ModelComponent sample = modelComponents.iterator().next();
                final String name = sample.getAddress().getDomainName();
                final long version = sample.getAddress().getDomainVersion();
                DomainModel model;
                try {
                    model = new ImmutableDomainModel(name, version, modelComponents);
                    newModels.add(model); //add to models to register
                } catch (ModelException ex) {
                    logger.log(Level.SEVERE, "Problems creating domain model '"
                            + DomainUtil.uniqueDomainId(name, version)
                            + "'; domain will not be registered.", ex);
                    continue; //do not register
                }
            }
    }

            @Override
            public String getDomainId
            
                () {
        return model.getDomainId();
            }

            @Override
            public long getVersion
            
                () {
        return model.getVersion();
            }

            @Override
            public String getName
            
                () {
        return model.getName();
            }

            @Override
            public Collection<ModelComponent> findAll
            
                () {
        return model.findAll();
            }

            @Override
            public Optional<ModelComponent> find
            (ComponentAddress address
            
                ) {
        return model.find(address);
            }

            @Override
            public Collection<Attributed> find
            (Class<? extends ModelAttribute> attributeType
            
                ) {
        return model.find(attributeType);
            }

            @Override
            public Collection<ObjectModel<?>> findSpecialized
            (ComponentAddress parentAddress
            
                ) {
        return model.findSpecialized(parentAddress);
            }

            @Override
            public String toString
            
                () {
        return model.toString();
            }

        }
