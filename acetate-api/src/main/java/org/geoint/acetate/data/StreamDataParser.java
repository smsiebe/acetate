package org.geoint.acetate.data;

import java.io.InputStream;

/**
 * Stream-based parser.
 *
 * @param <T> resultant java object type from parsing
 */
public interface StreamDataParser<T> extends DataParser<InputStream, T> {

}
