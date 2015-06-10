package org.geoint.acetate.model.address;

/**
 *
 */
public interface ObjectAddress extends ComponentAddress {

    /**
     * Domain-unique object name.
     *
     * @return domain-unique object name
     */
    String getObjectName();

}
