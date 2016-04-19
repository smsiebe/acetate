/*
 * Copyright 2016 geoint.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geoint.acetate;

import java.util.Collection;
import java.util.Objects;
import org.geoint.acetate.model.ComposedType;
import org.geoint.acetate.model.InvalidModelException;
import org.geoint.acetate.util.ImmutableKeyedSet;

/**
 * An instance of a domain composite type.
 *
 * @author steve_siebert
 * @param <M> composite type
 */
public class CompositeInstance<M extends ComposedType> extends TypeInstance<M> {

    protected final ImmutableKeyedSet<String, InstanceRef> composites;

    protected CompositeInstance(M typeModel,
            ImmutableKeyedSet<String, InstanceRef> composites) {
        super(typeModel);
        this.composites = composites;
    }

    /**
     * Returns InstanceRef for each model-defined composite.
     * <p>
     * Note an InstanceRef will exist for each model-defined composite type even
     * if this instance does not have a composite instance.
     *
     * @return composite instances
     */
    public Collection<InstanceRef> getComposites() {
        return composites;
    }

    /**
     * Retrieve an instance reference to a model-defined composite type.
     * <p>
     * Note this method will always return an appropriate InstanceRef instance
     * for the composite name even if the composite instance is null for this
     * instance.
     *
     * @param compositeRefName composite reference name
     * @return composite or null
     * @throws InvalidModelException if the requested composite ref name is not
     * a valid, model-defined, reference name for a composite
     */
    public InstanceRef findComposite(String compositeRefName)
            throws InvalidModelException {
        return composites.getOrThrow(compositeRefName,
                () -> new InvalidModelException(String.format("%s does not "
                        + "define a composite named '%s'",
                        getModel().toString(),
                        compositeRefName)));
    }

    @Override
    public String toString() {
        return String.format("Instance of composite type '%s'",
                typeModel.toString());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.typeModel);
        hash = 29 * hash + Objects.hashCode(this.composites);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompositeInstance<?> other = (CompositeInstance<?>) obj;
        if (!Objects.equals(this.typeModel, other.typeModel)) {
            return false;
        }
        return Objects.equals(this.composites, other.composites);
    }

}
