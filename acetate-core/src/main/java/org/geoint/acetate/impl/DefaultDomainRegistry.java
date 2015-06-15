package org.geoint.acetate.impl;

import gov.ic.geoint.acetate.DomainRegistry;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.geoint.acetate.impl.model.entity.ImmutableDomainModel;
import org.geoint.acetate.impl.model.scan.AsyncModelScanner;
import org.geoint.acetate.impl.model.scan.ClassLoaderModelScanner;
import org.geoint.acetate.impl.model.scan.ModelScanManager;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.model.scan.ModelScanResults;
import org.geoint.acetate.model.scan.ModelScanner;

/**
 *
 */
public class DefaultDomainRegistry implements DomainRegistry {

    private static ExecutorService exe;
    private final ModelScanManager scans = new ModelScanManager();

    /**
     * Sets the executor to use for all domain registry functions.
     *
     * @param executor
     */
    public static synchronized void setExecutor(ExecutorService executor) {
        exe = executor;
    }

    /**
     * Scans for components discoverable from the system class loader.
     *
     * @return registered loaded with the domain models found on the system
     * class loader
     */
    @Override
    public Future<ModelScanResults> scan() {
        return executeScan(new ClassLoaderModelScanner());
    }

    @Override
    public Future<ModelScanResults> scan(ModelScanner scanner) {

    }

    @Override
    public boolean isRegistered(String domainModelName, long version) {

    }

    @Override
    public Optional<DomainModel> getModel(String domainModelName, long version) {

    }

    private Future<ModelScanResults> executeScan(ModelScanner scanner) {
        ExecutorService e = getExecutor();
        return scans.execute(e, new AsyncModelScanner(e,
                ImmutableDomainModel::fromComponents,
                scanner)
        );
    }

    private static synchronized ExecutorService getExecutor() {
        if (exe == null) {
            exe = Executors.newCachedThreadPool();
        }
        return exe;
    }

    @Override
    public void register(DomainModel model) throws ModelException {

    }

}
