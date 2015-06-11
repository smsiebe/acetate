package org.geoint.acetate.impl.model;

import static org.geoint.acetate.impl.model.ImmutableObjectAddress.DOMAIN_VERSION_SEPARATOR;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.ViewModel;

/**
 *
 * @param <T>
 */
public final class ImmutableViewModel<T> implements ViewModel<T> {

    private final String viewName;
    private final ObjectModel<T> baseModel;

    public ImmutableViewModel(String viewName, ObjectModel<T> baseModel) {
        this.viewName = viewName;
        this.baseModel = baseModel;
    }

    /**
     * Address for a view.
     */
    public final class ImmutableViewAddress extends ImmutableObjectAddress {

        private final ImmutableBaseObjectAddress domainObject;
        private final String viewName;

        private static final String VIEW_SCHEME = "view://";

        public ImmutableViewAddress(ImmutableBaseObjectAddress domainObject,
                String viewName) {
            this.domainObject = domainObject;
            this.viewName = viewName;
        }

        @Override
        public String getDomainName() {
            return domainObject.getDomainName();
        }

        @Override
        public long getDomainVersion() {
            return domainObject.getDomainVersion();
        }

        @Override
        public String asString() {
            return VIEW_SCHEME
                    + getDomainName()
                    + DOMAIN_VERSION_SEPARATOR
                    + getDomainVersion()
                    + COMPONENT_SEPARATOR
                    + viewName
                    + COMPONENT_SEPARATOR
                    + domainObject.getObjectName();
        }

    }

}
