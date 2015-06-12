package gov.ic.geoint.acetate;

import java.util.Optional;
import java.util.concurrent.Future;
import org.geoint.acetate.model.DomainModel;
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
     * @return future containing this domain registry
     */
    Future<DomainRegistry> scan();

    /**
     * Asynchronously scan for all domain models discoverable by the provided
     * {@link ModelScanner}.
     *
     * Calling this method starts an asynchronous scan operation which will scan
     * for, and register, domain models with the registry (thread-safe
     * operation).
     *
     * @param scanner scanner used to discover domain models
     * @return future containing this domain registry
     */
    Future<DomainRegistry> scan(ModelScanner scanner);

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

}
