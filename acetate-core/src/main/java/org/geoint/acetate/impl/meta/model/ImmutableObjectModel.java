package org.geoint.acetate.impl.meta.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import org.geoint.acetate.meta.model.ObjectModel;
import org.geoint.acetate.meta.model.OperationModel;

/**
 *
 * @param <T>
 */
public class ImmutableObjectModel<T> implements ObjectModel<T> {

    private final Class<T> type;
    private final Map<String, String> attributes;
    private final Collection<OperationModel> operations;
    private final Collection<ObjectModel<? super T>> parents;
    private final Collection<ObjectModel<? extends T>> specialized;

    public ImmutableObjectModel(Class<T> type,
            Map<String, String> attributes,
            Collection<ImmutableOperationModel> operations,
            Collection<ImmutableObjectModel<? super T>> parents,
            Collection<ImmutableObjectModel<? extends T>> specialized) {
        this.type = type;
        this.attributes = Collections.unmodifiableMap(attributes);
        this.operations = Collections.unmodifiableCollection(operations);
        this.parents = Collections.unmodifiableCollection(parents);
        this.specialized = Collections.unmodifiableCollection(specialized);
    }

    @Override
    public Class<T> getObjectType() {
        return type;
    }

    @Override
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public Optional<String> getAttribute(String attributeName) {
        return Optional.ofNullable(attributes.get(attributeName));
    }

    @Override
    public Collection<OperationModel> getOperations() {
        return operations;
    }

    @Override
    public Collection<ObjectModel<? super T>> getParents() {
        return parents;
    }

    @Override
    public Collection<ObjectModel<? extends T>> getSpecialized() {
        return specialized;
    }

}
