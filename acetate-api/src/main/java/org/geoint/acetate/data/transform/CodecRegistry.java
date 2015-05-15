package org.geoint.acetate.data.transform;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.ModelContextPath;

/**
 * Registry for a domain models codecs.
 *
 * The codec registry can be retrieved programmatically to search and retrieve
 * codecs associated with a domain model. This may be useful, for example, for
 * composite objects.
 */
public interface CodecRegistry {

    /**
     * All codecs registered to this domain model.
     *
     * @return immutable collection of all registered codecs, if none returns an
     * empty collection
     */
    Collection<ObjectCodec<?>> findAll();

    /**
     * Retrieve all codecs that could be used with this type.
     *
     * @param <T>
     * @param type
     * @return immutable collection of all codecs that could be used for the
     * specified type, if none return an empty collection.
     */
    <T> Collection<ObjectCodec<T>> find(Class<T> type);

    /**
     * Return the codec to use for the defined path.
     *
     * @param <T>
     * @param type
     * @param context
     * @return codec specifically assigned to this context
     */
    <T> Optional<ObjectCodec<T>> find(Class<T> type, ModelContextPath context);

    /**
     * Return the default codec for the class.
     *
     * @param <T>
     * @param type
     * @return default codec for class
     */
    <T> Optional<ObjectCodec<T>> findDefault(Class<T> type);
}
