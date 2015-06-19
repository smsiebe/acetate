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
import org.geoint.acetate.model.ImmutableModelComponent;
import org.geoint.acetate.impl.model.scan.ModelScanManager;
import org.geoint.acetate.model.DomainId;
import org.geoint.acetate.model.DomainModel;
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
        return scans.execute(new MetaModelScanner(),
                new ModelScanCompleteRegister());
    }

    @Override
    public Future<ModelScanResults> scan(ModelScanner scanner) {
        return scans.execute(scanner, new ModelScanCompleteRegister());
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

                if (models.containsKey(m.getId())) {
                    //TODO merge domain models
                    logger.log(Level.SEVERE, "Domain model {0} is already "
                            + "registered.", m.getId());
                    continue;
                }

                models.put(m.getId(), m);
            }
        }
    }

    /**
     * Register domain model components once the scan is complete.
     */
    private final class ModelScanCompleteRegister implements ModelScanListener {

        private final Map<DomainId, Collection<ImmutableModelComponent>> components
                = new HashMap<>();

        @Override
        public void component(ImmutableModelComponent component) {
            final DomainId id = component.getDomainId();
            if (!components.containsKey(id)) {
                components.put(id, new ArrayList<>());
            }
            components.get(id).add(component);
        }

        @Override
        public void scanComplete(ModelScanner scanner, ModelScanResults results) {

            Collection<DomainModel> models = new ArrayList<>();
            for (Entry<DomainId, Collection<ImmutableModelComponent>> e
                    : components.entrySet()) {
                try {
                    models.add(new ImmutableDomainModel(e.getKey(), e.getValue()));
                } catch (ModelException ex) {
                    logger.log(Level.SEVERE, "Unable to register domain '"
                            + e.getKey().asString(), ex);
                }
            }
            register(models);
        }
    }
}
