package org.geoint.acetate.impl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.geoint.acetate.impl.model.scan.DomainModelFactory;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.ModelException;

/**
 *
 */
public final class ImmutableDomainModel extends InMemoryDomainModel {

    public ImmutableDomainModel(String name,
            long version,
            String displayName,
            String description,
            Collection<ModelComponent> components)
            throws ModelException {
        super(name, version, displayName, description, components);
    }

    /**
     * Constructs instances of ImmutableDomainModel containing the provided
     * components.
     *
     * This method intentionally implements the {@link DomainModelFactory}
     * functional interface so that it may be passed by reference.
     *
     * @param components
     * @return models
     * @throws ModelException thrown if a domain model is invalid
     */
    public static Collection<DomainModel> fromComponents(
            Collection<ModelComponent> components)
            throws ModelException {

        Map<String, Map<String, ModelComponent>> bucketized
                = new HashMap<>();
        for (ModelComponent c : components) {

            String domainId = DomainUtil.uniqueDomainId(c.getAddress());
            if (!bucketized.containsKey(domainId)) {
                bucketized.put(domainId, new HashMap<>());
            }

            if (bucketized.get(domainId).containsKey(c.getName())) {
                throw new ModelException(c.getAddress().getDomainName(),
                        c.getAddress().getDomainVersion(),
                        "Duplicate model component '"
                        + c.getName() + "' was already found in "
                        + "domain '" + domainId + "'.");
            }

            bucketized.get(domainId).put(c.getName(), c);
        }

        //TODO add validation to ensure domain model is complete
        Collection<DomainModel> models = new ArrayList<>();
        for (Entry<String, Map<String, ModelComponent>> e : bucketized.entrySet()) {
            final Collection<ModelComponent> modelComponents = e.getValue().values();
            if (modelComponents.isEmpty()) {
                continue;
            }
            final ModelComponent sample = modelComponents.iterator().next();
            final String name = sample.getAddress().getDomainName();
            final long version = sample.getAddress().getDomainVersion();
            models.add(new ImmutableDomainModel(name, version, null, null, components));
        }
        return models;
    }
}
