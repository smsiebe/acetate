package org.geoint.acetate.impl.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.geoint.acetate.bind.transform.ObjectConverter;
import org.geoint.acetate.bind.transform.DataTransformException;
import org.geoint.acetate.impl.transform.DefaultMapConverter.KeyValue;
import org.geoint.acetate.model.DataModel;

/**
 * Default {@link Map java.util.Map} converter, converting each map entry into a
 * domain object where the key is accessible from the <i>key</i>
 * component and the value from the <i>value</i> component.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class DefaultMapConverter<K, V>
        implements ObjectConverter<Map<K, V>, Collection<KeyValue<K, V>>> {

    @Override
    public Optional<Collection<KeyValue<K, V>>> convert(
            DataModel<Collection<KeyValue<K, V>>> model, Map<K, V> obj)
            throws DataTransformException {
        Collection<KeyValue<K, V>> coll = new ArrayList<>();
        obj.entrySet().stream()
                .map((e) -> new KeyValue(e.getKey(), e.getValue()))
                .forEach((kv) -> coll.add(kv));
        return Optional.ofNullable(coll);
    }

    @Override
    public Optional<Map<K, V>> invert(
            DataModel<Collection<KeyValue<K, V>>> model, Collection<KeyValue<K, V>> modeled)
            throws DataTransformException {
        Map<K, V> map = new LinkedHashMap<>();
        modeled.stream().forEach((p) -> {
            map.put(p.key, p.value);
        });
        return Optional.ofNullable(map);
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
