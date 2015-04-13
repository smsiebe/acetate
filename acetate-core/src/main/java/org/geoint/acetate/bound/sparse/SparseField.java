package org.geoint.acetate.bound.sparse;

import java.nio.charset.Charset;

/**
 * Data that did not map to the data model.
 */
public interface SparseField {

    String getPath();

    String asString(Charset charset);

    byte[] asBytes();

    <T> T asObject(Class<T> type);

}
