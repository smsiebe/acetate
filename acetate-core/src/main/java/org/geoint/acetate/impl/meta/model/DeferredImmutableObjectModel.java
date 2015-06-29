package org.geoint.acetate.impl.meta.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.meta.MetaVersion;
import org.geoint.acetate.meta.model.ModelException;
import org.geoint.acetate.meta.model.ObjectModel;
import org.geoint.acetate.meta.model.OperationModel;

/**
 * Deferred object model which is constructed with a method reference (promise)
 * to create an ImmutableObjectModel when called.
 */
final class DeferredImmutableObjectModel implements ObjectModel {

    private final DomainId domainId;
    private Supplier<ImmutableObjectModel> supplier;
    private ImmutableObjectModel deferredModel;

    private static final Logger logger
            = Logger.getLogger(DeferredImmutableObjectModel.class.getName());

    public DeferredImmutableObjectModel(DomainId domainId,
            Supplier<ImmutableObjectModel> supplier) {
        this.domainId = domainId;
        this.supplier = supplier;
    }

    @Override
    public String getName() {
        return this.domainId.getObjectName();
    }

    @Override
    public String getDomainName() {
        return this.domainId.getDomainName();
    }

    @Override
    public MetaVersion getDomainVersion() {
        return this.domainId.getDomainVersion();
    }

    @Override
    public Map<String, String> getAttributes() {
        try {
            return model().getAttributes();
        } catch (ModelException ex) {
            logDeferredError("attributes", ex);
        }
        return Collections.EMPTY_MAP;
    }

    @Override
    public Optional<String> getAttribute(String attributeName) {
        return Optional.ofNullable(getAttributes().get(attributeName));
    }

    @Override
    public Collection<OperationModel> getDeclaredOperations() {
        try {
            return model().getDeclaredOperations();
        } catch (ModelException ex) {
            logDeferredError("declared operations", ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<OperationModel> getOperations() {
        try {
            return model().getOperations();
        } catch (ModelException ex) {
            logDeferredError("all operations", ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ObjectModel> getParents() {
        try {
            return model().getParents();
        } catch (ModelException ex) {
            logDeferredError("inherited models", ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ObjectModel> getSpecialized() {
        try {
            return model().getSpecialized();
        } catch (ModelException ex) {
            logDeferredError("specialized models", ex);
        }
        return Collections.EMPTY_LIST;
    }

    private void logDeferredError(String attemptedMethod, Throwable ex) {
        logger.log(Level.SEVERE, "Unable to retreive " + attemptedMethod + " info "
                + "for '" + domainId.asString() + "'", ex);
    }

    /**
     * Return deferred model, instantiating it as needed.
     *
     * @return
     * @throws ModelException
     */
    private synchronized ImmutableObjectModel model() throws ModelException {
        if (deferredModel != null) {
            try {
                deferredModel = supplier.get();
            } catch (Throwable ex) {
                throw new DeferredModelException(domainId, ex);
            }
            supplier = null; //free up reference
        }
        return deferredModel;
    }
}
