package org.geoint.acetate.impl.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.Inherited;
import org.geoint.acetate.model.ModelContextPath;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.event.DomainEntityEvent;

/**
 *
 * @param <R>
 */
public class InheritedDomainOperation<R> extends DomainOperationImpl<R>
        implements Inherited<DomainObject<?>> {

    private final String inheritedFrom;

    public InheritedDomainOperation(DomainModel model, String inheritedFrom,
            ModelContextPath path, String name, Optional<String> description,
            DomainEntityEvent<R, ?> returned, Collection<DomainObject<?>> params,
            Collection<? extends ComponentAttribute> attributes) {
        super(model, path, name, description, returned, params, attributes);
        if (inheritedFrom == null) {
            throw new NullPointerException("Parent domain model name must not "
                    + "be null.");
        }
        this.inheritedFrom = inheritedFrom;
    }

    @Override
    public Collection<DomainObject<?>> inheritsFrom() {
        DomainObject<?>[] parents
                = {model.getComponents()
                    .findByName(inheritedFrom).get()
                };
        return Arrays.asList(parents);
    }

}
