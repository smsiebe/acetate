package org.geoint.acetate.impl;

import org.geoint.acetate.DomainRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.geoint.acetate.impl.model.DomainUtil;
import org.geoint.acetate.impl.model.ImmutableDomainModel;
import org.geoint.acetate.impl.model.scan.ModelScanManager;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.model.scan.ModelScanResults;
import org.geoint.acetate.model.scan.ModelScanner;

/**
 *
 */
public class DefaultDomainRegistry implements DomainRegistry {

    private final ExecutorService exe;
    private final ModelScanManager scans;
    private final Map<String, DomainModel> models = new HashMap<>();

    public DefaultDomainRegistry() {
        this(Executors.newCachedThreadPool());
    }

    public DefaultDomainRegistry(ExecutorService exe) {
        this.exe = exe;
        this.scans = new ModelScanManager(this,
                ImmutableDomainModel::fromComponents,
                exe);
    }

    /**
     * Scans for components discoverable from the system class loader.
     *
     * @return registered loaded with the domain models found on the system
     * class loader
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
            return models.containsKey(DomainUtil.uniqueDomainId(domainModelName, version));
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

    @Override
    public void register(DomainModel... newModels) throws ModelException {
        synchronized (models) {
            for (DomainModel m : newModels) {
                if (models.containsKey(m.getDomainId())) {
                    throw new ModelException(m.getDisplayName(), m.getVersion(),
                            "Domain model "
                            + m.getDomainId()
                            + " is already registered."
                    );
                }

                models.put(m.getDomainId(), m);
            }
        }
    }

}
