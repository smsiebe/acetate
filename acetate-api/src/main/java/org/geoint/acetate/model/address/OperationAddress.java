package org.geoint.acetate.model.address;

/**
 * Domain model address for a model operation.
 */
public interface OperationAddress extends CompositeAddress {

    /**
     * Component address for the return type of this operation.
     *
     * @return address for this operations return type
     */
    ObjectAddress getReturnAddress();

    /**
     * Component address for the specified parameter for this operation.
     *
     * @param paramName parameter name
     * @return address for the operation parameter
     */
    ObjectAddress getParameterAddress(String paramName);

    /**
     * Addresses for the parameters for this operation.
     *
     * @return addresses for each operation parameter, indexed in the order in
     * which they exist in the operation signature
     */
    ObjectAddress[] getParameterAddresses();
}
