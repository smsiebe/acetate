package org.geoint.acetate.impl.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.impl.codec.DefaultBooleanCodec;
import org.geoint.acetate.impl.codec.DefaultIntegerCodec;
import org.geoint.acetate.impl.codec.DefaultStringCodec;
import static org.geoint.acetate.impl.model.DomainModelBuilder.defaultModel;
import org.geoint.acetate.model.ComponentConstraint;
import org.geoint.acetate.model.ComponentModel;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;

/**
 *
 */
public class DomainModelBuilder {

    private final String name;
    private final long version;
    private String description;
    private Map<String, ComponentModelBuilderImpl<?>> components;
    private static DomainModel DEFAULT_MODEL;

    private DomainModelBuilder(String modelName, long modelVersion) {
        this.name = modelName;
        this.version = modelVersion;
    }

    /**
     * Create a new domain model, from scratch, without any model defaults (ie
     * default components, codecs, etc).
     *
     * @param modelName new domain model name
     * @param modelVersion new domain model version
     * @return empty domain model
     */
    public static DomainModelBuilder newModel(String modelName, long modelVersion) {
        return new DomainModelBuilder(modelName, modelVersion);
    }

    /**
     * Creates a new domain model by extending from an existing model.
     *
     * @param modelName new domain model name
     * @param modelVersion new domain model version
     * @param model model to copy from
     * @return domain model seeded from provided model
     */
    public static DomainModelBuilder extend(String modelName, long modelVersion, DomainModel model) {
        DomainModelBuilder b = new DomainModelBuilder(modelName, modelVersion);
        b.copyFrom(model);
        return b;
    }

    /**
     * Creates a new domain model seeding common model artifacts from the
     * default domain model.
     *
     * @param modelName new domain model name
     * @param modelVersion new domain model version
     * @return domain model builder seeded from default domain model
     */
    public static DomainModelBuilder newDefault(String modelName, long modelVersion) {
        return extend(modelName, modelVersion, defaultModel());
    }

    public DomainModelBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ComponentModelBuilder<?> component(String componentName) {
        return component(componentName, null);
    }

    public <T> ComponentModelBuilder<T> component(String componentName,
            Class<T> componentType) {
        if (components.containsKey(componentName)) {
            components.put(componentName,
                    new ComponentModelBuilderImpl(componentName, componentType));
        }
        return (ComponentModelBuilder<T>) components.get(componentName);
    }

    public DomainModel build() {

        Map<String, ComponentModel<?>> builtComponents = new HashMap<>();

        for (Entry<String, ComponentModelBuilderImpl<?>> e : components.entrySet()) {
            //for each component
            ComponentModel<?> cm;
            final ComponentModelBuilderImpl<?> cb = e.getValue();
            
            if (cb.componentType != null) {
                cm =  6  
            }
            for (Entry<String, String> ce : cb.compositeComponents.entrySet()) {
                // ensure that composite component is registered with the domain model
            }
        }
        if (components.entrySet().parallelStream()
                .map((e) -> e.getValue())
                .map((cn) -> cn.compositeComponents)
                .map((e) -> e.getValue())
                .anyMatch((ccn) -> !components.containsKey(ccn))) {
            //ensure all defined composite components have a registered component
            //in the domain model
        }
    }

    public static DomainModel defaultModel() {
        if (DEFAULT_MODEL == null) {
            DomainModelBuilder db = new DomainModelBuilder("DEFAULT", 1);
            db.component("java.util.String", String.class)
                    .codec(new DefaultStringCodec());
            db.component("java.lang.Integer", Integer.class)
                    .codec(new DefaultIntegerCodec());
            db.component("java.lang.Boolean", Boolean.class)
                    .codec(new DefaultBooleanCodec());

            //TODO add the rest
            DEFAULT_MODEL = db.build();
        }
        return DEFAULT_MODEL;
    }

    /**
     * Copies the state of the provided domain model to this model.
     *
     * @param model
     */
    private void copyFrom(DomainModel model) {

    }

    private class ComponentModelBuilderImpl<T> implements ComponentModelBuilder<T> {

        private final String componentName;
        private final Class<T> componentType;
        private ObjectCodec<T> codec;
        private final Set<ComponentConstraint> constraints = new HashSet<>();
        private final Set<ComponentAttribute> attributes = new HashSet<>();
        //key = local name, value = composite component name
        private final Map<String, String> compositeComponents = new TreeMap<>();

        public ComponentModelBuilderImpl(String componentName) {
            this.componentName = componentName;
            this.componentType = null;
        }

        public ComponentModelBuilderImpl(String componentName, Class<T> componentType) {
            this.componentName = componentName;
            this.componentType = componentType;
        }

        @Override
        public ComponentModelBuilder<T> constraint(ComponentConstraint constraint) {
            constraints.add(constraint);
            return this;
        }

        @Override
        public ComponentModelBuilder<T> attribute(ComponentAttribute attribute) {
            this.attributes.add(attribute);
            return this;
        }

        @Override
        public ComponentModelBuilder<T> composite(String localName, String componentName) {
            compositeComponents.put(localName, componentName);
            return this;
        }

        @Override
        public ComponentModelBuilder<T> codec(ObjectCodec<T> codec) {
            this.codec = codec;
            return this;
        }
    }
}
