package org.geoint.acetate;

import java.util.Optional;
import java.util.concurrent.Future;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.model.scan.ModelScanResults;
import org.geoint.acetate.model.scan.ModelScanner;

/**
 * Model registry for domain models, their components, and acetate layers
 * defined against them.
 *
 * All operations on DomainRegistry implementations must be atomic and
 * thread-safe.
 */
public interface DomainRegistry {

    /**
     * Asynchronously scan for all domain models discoverable by the registry
     * default {@link ModelScanner}.
     *
     * Calling this method starts an asynchronous scan operation which will scan
     * for, and register, domain models with the registry (thread-safe
     * operation).
     *
     * Upon successful completion of the scan (which may be checked by calling
     * {@link Future#isDone() isDone} on the returned Future), the model will be
     * registered with this registry.
     *
     * @return async results of the scan
     */
    Future<ModelScanResults> scan();

    /**
     * Asynchronously scan for all domain models discoverable by the provided
     * {@link ModelScanner}.
     *
     * Calling this method starts an asynchronous scan operation which will scan
     * for, and register, domain models with the registry (thread-safe
     * operation).
     *
     * Upon successful completion of the scan (which may be checked by calling
     * {@link Future#isDone() isDone} on the returned Future), the model will be
     * registered with this registry
     *
     * @param scanner scanner used to discover domain models
     * @return async results of the scan
     */
    Future<ModelScanResults> scan(ModelScanner scanner);

    /**
     * Check if the requested domain model is already registered.
     *
     * @param domainModelName name of the domain model
     * @param version version of the domain model
     * @return true if registered, otherwise false
     */
    boolean isRegistered(String domainModelName, long version);

    /**
     * Returns the requested domain model, if registered.
     *
     * @param domainModelName globally unique domain model name
     * @param version domain model version
     * @return domain model, if registered
     */
    Optional<DomainModel> getModel(String domainModelName, long version);

    /**
     * Register one or more domain models atomically with the registry.
     *
     * @param models models to register
     * @throws ModelException thrown if the provided model is invalid or
     * conflicts with another model within the registry
     */
    void register(DomainModel... models) throws ModelException;
}
