package org.geoint.acetate.model;

import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.transform.DataConversionException;

/**
 * A DataType which directly converts binary data to/from a java object.
 *
 * BinaryDataType instances <b>SHOULD</b> clearly define the binary data format
 * supported in its javadoc.
 *
 * Acetate only requires a BinaryDataType when it's necessary to bind binary
 * data directly to a Java object. Typical data binding scenarios, where
 * application-defined java objects are bound to data formats such as XML or
 * JSON, acetate can instantiate the java object without the need for a custom
 * data type. Custom BinaryDataType can, potentially, increase the performance
 * of a binding operation, but may result in data-format specific data type
 * definitions.
 *
 * @param <T> the java object value type of this data type
 */
public interface BinaryDataType<T> extends DataType<T> {

    /**
     * Instantiates the java object instance from the provided binary data.
     *
     * @param reader value bytes
     * @throws DataConversionException thrown if the data could not be converted
     * to the respective data type
     * @return instantiated java object
     */
    T intantiate(ByteReader reader) throws DataConversionException;
}
