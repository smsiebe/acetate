package org.geoint.acetate.impl.transform;

import java.util.Map;
import org.geoint.acetate.data.transform.DataConversionException;
import org.geoint.acetate.data.transform.ComplexObjectCodec;
import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.io.ByteWriter;
import org.geoint.acetate.model.ModelContextPath;
import org.geoint.acetate.model.DomainModel;

/**
 * Default binary codec for {@link Map java.util.Map} and its subclasses.
 *
 * @param <K>
 * @param <V>
 */
public class DefaultMapCodec<K, V> extends ComplexObjectCodec<Map<K, V>> {

    public DefaultMapCodec(DomainModel model, ModelContextPath path) {
        super(model, path);
    }

    @Override
    public Map<K, V> convert(ByteReader reader)
            throws DataConversionException {
        
    }

    @Override
    public void invert(Map<K, V> data, ByteWriter writer)
            throws DataConversionException {

    }

}
