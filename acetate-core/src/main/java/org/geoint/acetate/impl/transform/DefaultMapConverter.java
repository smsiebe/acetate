package org.geoint.acetate.impl.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.geoint.acetate.data.transform.Converter;
import org.geoint.acetate.data.transform.DataConversionException;
import org.geoint.acetate.impl.transform.DefaultMapConverter.KeyValue;

/**
 * Default {@link Map java.util.Map} converter, converting each map entry into a
 * domain object where the key is accessible from the <i>key</i>
 * component and the value from the <i>value</i> component.
 */
public class DefaultMapConverter<K, V>
        implements Converter<Map<K, V>, Collection<KeyValue<K, V>>> {

    @Override
    public Collection<KeyValue<K, V>> convert(Map<K, V> from)
            throws DataConversionException {
        Collection<KeyValue<K, V>> coll = new ArrayList<>();
        from.entrySet().stream()
                .map((e) -> new KeyValue(e.getKey(), e.getValue()))
                .forEach((kv) -> coll.add(kv));
        return coll;
    }

    @Override
    public Map<K, V> invert(Collection<KeyValue<K, V>> to)
            throws DataConversionException {
        Map<K, V> map = new LinkedHashMap<>();
        to.stream().forEach((p) -> {
            map.put(p.key, p.value);
        });
        return map;
    }

    /**
     * Simple key/value transient domain object.
     *
     * @param <K>
     * @param <V>
     */
    public static class KeyValue<K, V> {

        private final K key;
        private final V value;

        public KeyValue(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "key: '" + key + "'; value: '" + value + "'";
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 19 * hash + Objects.hashCode(this.key);
            hash = 19 * hash + Objects.hashCode(this.value);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final KeyValue<?, ?> other = (KeyValue<?, ?>) obj;
            if (!Objects.equals(this.key, other.key)) {
                return false;
            }
            if (!Objects.equals(this.value, other.value)) {
                return false;
            }
            return true;
        }

    }
}
