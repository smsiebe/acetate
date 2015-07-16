package org.geoint.acetate.model.common;

import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.domain.model.DataModel;

/**
 * Basic Key/Value domain model object.
 * 
 * @param <K> key type
 * @param <V> value type
 */
@Model(domain = DataModel.COMMON_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION,
        name = "kv",
        displayName = "keyValue")
public final class KeyValue<K, V> {

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
        return String.format("%s$ : %s$", key.toString(), value.toString());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.key != null ? this.key.hashCode() : 0);
        hash = 23 * hash + (this.value != null ? this.value.hashCode() : 0);
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
        if (this.key != other.key && (this.key == null || !this.key.equals(other.key))) {
            return false;
        }
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

}
