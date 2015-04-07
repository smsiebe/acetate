package org.geoint.acetate.bound.impl;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.bound.sparse.SparseField;
import org.geoint.acetate.bound.BoundComponent;
import org.geoint.acetate.bound.BoundData;
import org.geoint.acetate.bound.BoundField;
import org.geoint.acetate.model.DataConstraintException;
import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.model.FieldModel;
import org.geoint.acetate.model.ModelConstraintException;
import org.geoint.concurrent.ThreadSafe;
import org.geoint.util.hierarchy.HierarchicalPath;
import org.geoint.util.hierarchy.StandardHierarchy;

/**
 * Default BoundData implementation which maintains the data binding as a
 * hierarchy of bound components.
 */
@ThreadSafe
public class HierarchicalBoundDataImpl implements BoundData {

    private final DataModel model;
    private final StandardHierarchy<HierarchicalBoundComponent> root;
    private final Collection<? extends SparseField> sparse;

    HierarchicalBoundDataImpl(DataModel model,
            StandardHierarchy<HierarchicalBoundComponent> root,
            Collection<? extends SparseField> sparse) {
        this.root = root;
        this.model = model;
        this.sparse = sparse;
    }

    @Override
    public String getGUID() {
        FieldModel<?> guidModel = model.getGUID();
        return ((BoundField) get(
                guidModel.getPath())
                .get().iterator().next())
                .asString();
    }

    @Override
    public Optional<String> getVersion() {
        Optional<FieldModel<?>> fieldModel = model.getVersion();
        if (!fieldModel.isPresent()) {
            return Optional.empty();
        }
        Optional<Collection<BoundComponent>> version
                = get(fieldModel.get().getPath());
        if (!version.isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(((BoundField) version
                .get().iterator().next())
                .asString());
    }

    @Override
    public BoundComponent get() {
        return root.getValue();
    }

    @Override
    public Optional<Collection<BoundComponent>> get(String path) {
        return Optional.ofNullable(
                root.get(HierarchicalPath.instance(path))
                .getValue().getComponents());
    }

    @Override
    public Collection<? extends SparseField> getSparse() {
        return sparse;
    }

    @Override
    public DataModel getModel() {
        return model;
    }

    @Override
    public void validate()
            throws ModelConstraintException, DataConstraintException {
        this.validateModel();
        this.validateData();
    }

    @Override
    public void validateModel() throws ModelConstraintException {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void validateData() throws DataConstraintException {
        //TODO
        throw new UnsupportedOperationException();
    }

}
