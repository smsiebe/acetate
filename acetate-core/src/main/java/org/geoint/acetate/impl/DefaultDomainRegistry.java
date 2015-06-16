package org.geoint.acetate.impl;

import java.util.ArrayList;
import java.util.Collection;
import org.geoint.acetate.DomainRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.impl.model.DomainUtil;
import org.geoint.acetate.impl.model.ImmutableDomainModel;
import org.geoint.acetate.impl.model.scan.ModelScanManager;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.model.scan.ModelScanListener;
import org.geoint.acetate.model.scan.ModelScanResults;
import org.geoint.acetate.model.scan.ModelScanner;

/**
 *
 */
public class DefaultDomainRegistry implements DomainRegistry {

    private final ExecutorService exe;
    private final ModelScanManager scans;
    private final Map<String, DomainModel> models = new HashMap<>();

    private static final Logger logger
            = Logger.getLogger(DefaultDomainRegistry.class.getName());

    public DefaultDomainRegistry() {
        this(Executors.newCachedThreadPool());
    }

    public DefaultDomainRegistry(ExecutorService exe) {
        this.exe = exe;
        this.scans = new ModelScanManager(exe);
    }

    /**
     * Scans for components discoverable from metamodel data saved during
     * annotation processing.
     *
     * @return scan results
     */
    @Override
    public Future<ModelScanResults> scan() {
        //TODO add scanner which reads the annotation processer generated meta
        throw new UnsupportedOperationException();
        // return scans.execute(new AnnotationScanner());
    }

    @Override
    public Future<ModelScanResults> scan(ModelScanner scanner) {
        return scans.execute(scanner);
    }

    @Override
    public boolean isRegistered(String domainModelName, long version) {
        synchronized (models) {
            return models.containsKey(
                    DomainUtil.uniqueDomainId(domainModelName, version)
            );
        }
    }

    @Override
    public Optional<DomainModel> getModel(String domainModelName, long version) {
        synchronized (models) {
            return Optional.ofNullable(models.get(
                    DomainUtil.uniqueDomainId(domainModelName, version))
            );
        }
    }

    private void register(Collection<DomainModel> newModels) {
        synchronized (models) {
            for (DomainModel m : newModels) {

                if (models.containsKey(m.getDomainId())) {
                    //TODO merge domain models
                    logger.log(Level.SEVERE, "Domain model {0} is already "
                            + "registered.", m.getDomainId());
                    continue;
                }

                models.put(m.getDomainId(), m);
            }
        }
    }

    /**
     * Register domain model components once the scan is complete.
     */
    private final class ModelScanCompleteRegister implements ModelScanListener {

        @Override
        public void component(String modelName, long modelVersion,
                ModelComponent component) {
            //do nothing here
            //TODO add ability to add components to a domain model after registration
        }

        @Override
        public void scanComplete(ModelScanner scanner, ModelScanResults results) {

            //register "complete" domain models
            Map<String, Map<String, ModelComponent>> bucketized
                    = new HashMap<>();
            for (ModelComponent c : results.getComponents()) {

                String domainId = DomainUtil.uniqueDomainId(c.getAddress());
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
            for (Entry<String, Map<String, ModelComponent>> e : bucketized.entrySet()) {
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
            register(newModels);
        }

    }

}
