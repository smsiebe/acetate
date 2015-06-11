package org.geoint.acetate.impl.model;

import org.geoint.acetate.model.CompositeModel;

/**
 *
 */
public class ImmutableCompositeModel<T> extends ImmutableObjectModel<T>
        implements CompositeModel<T> {

    public class ImmutableCompositeAddress extends ImmutableObjectAddress {

        private final ImmutableObjectAddress containerAddress;
        private final String localName;

        public ImmutableCompositeAddress(
                ImmutableObjectAddress containerAddress,
                String localName) {
            this.containerAddress = containerAddress;
            this.localName = localName;
        }

        @Override
        public String getDomainName() {
            return containerAddress.getDomainName();
        }

        @Override
        public long getDomainVersion() {
            return containerAddress.getDomainVersion();
        }

        @Override
        public String asString() {

        }
    }

}
