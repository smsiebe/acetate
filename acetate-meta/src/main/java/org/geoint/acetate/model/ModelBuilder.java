package org.geoint.acetate.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.geoint.acetate.model.provider.LazyClassLoaderResolver;

/**
 * Fluid interface to build a complex model.
 * <p>
 * This builder is "stateful", remembering the context of the previous method
 * calls and either applying the current call to that context or, if not
 * logical, to the parent context. For example, calling first <i>method</i> and
 * then <i>annotation</i>, the annotation will be applied to the former method.
 * <p>
 * This class is <b>NOT</b> thread safe.
 */
public final class ModelBuilder {

    private final Map<String, ModelTypeBuilder> types = new HashMap<>(); //key is type class name
    private ModelElementBuilder currentBuilder;

    public ModelBuilder type(String className) {
        if (!types.containsKey(className)) {
            types.put(className, new ModelTypeBuilder(className));
        }
        currentBuilder = types.get(className);
        return this;
    }

    public ModelBuilder subclass(String className) {
        currentBuilder = currentBuilder.typeContext().subclass(className);
        return this;
    }

    public ModelBuilder field(String fieldName, String fieldClassName)
            throws IllegalStateException {
        currentBuilder = currentBuilder.typeContext().field(fieldName, fieldClassName);
        return this;
    }

    public ModelBuilder method(String methodName) throws IllegalStateException {
        currentBuilder = currentBuilder.typeContext().method(methodName);
        return this;
    }

    public ModelBuilder methodParameter(String name, String className)
            throws IllegalStateException {
        currentBuilder = currentBuilder.methodContext().parameter(name, className);
        return this;
    }

    public ModelBuilder methodException(String className)
            throws IllegalStateException {
        currentBuilder = currentBuilder.methodContext().exception(className);
        return this;
    }

    public ModelBuilder methodReturn(String className)
            throws IllegalStateException {
        currentBuilder = currentBuilder.methodContext().returnType(className);
        return this;
    }

    public ModelBuilder annotation(String annotationName)
            throws IllegalStateException {
        currentBuilder.annotation(annotationName);
        return this;
    }

    public ModelType[] build() {
        return types.values().stream()
                .map((tb) -> tb.build())
                .toArray((i) -> new ModelType[i]);
    }

    private abstract class ModelElementBuilder<M extends ModelElement> {

        protected final Set<ModelAnnotation<?>> annotations = new HashSet<>();

        public void annotation(String annotationName) {
            //TODO add support for transitive model annotations
            annotations.add(new ModelAnnotation(annotationName, new ModelAnnotation[0]));
        }

        /**
         * Return the contextually relevant method builder or throw exception if
         * builder cannot rationalize the builder context to return.
         *
         * @return builder for contextual method
         * @throws IllegalStateException
         */
        abstract ModelMethodBuilder methodContext() throws IllegalStateException;

        /**
         * Return the contextually relevant type builder or throw exception if
         * builder not rationalize the builder context to return.
         *
         * @return builder for contextual type
         * @throws IllegalStateException
         */
        abstract ModelTypeBuilder typeContext() throws IllegalStateException;

        abstract M build();
    }

    private class ModelTypeBuilder extends ModelElementBuilder<ModelType> {

        private final String typeClass;

        private final Map<String, ModelMethodBuilder> methods = new HashMap<>();
        private final Map<String, ModelFieldBuilder> fields = new HashMap<>();
        private final Set<String> subclassClassNames = new HashSet<>();
        private ModelType builtType;

        public ModelTypeBuilder(String typeClass) {
            this.typeClass = typeClass;
        }

        @Override
        ModelMethodBuilder methodContext() throws IllegalStateException {
            throw new IllegalStateException("Cannot switch to method context "
                    + "from type.");
        }

        @Override
        ModelTypeBuilder typeContext() throws IllegalStateException {
            return this;
        }

        private ModelFieldBuilder field(String fieldName, String fieldClassName) {
            if (fields.containsKey(fieldName)
                    && fields.get(fieldName).fieldClass
                    .contentEquals(fieldClassName)) {
                return fields.get(fieldName);
            }
            ModelFieldBuilder fb = new ModelFieldBuilder(this, fieldName, fieldClassName);
            fields.put(fieldName, fb);
            return fb;
        }

        private ModelMethodBuilder method(String methodName) {
            if (methods.containsKey(methodName)) {
                return methods.get(methodName);
            }
            ModelMethodBuilder mb = new ModelMethodBuilder(this, methodName);
            methods.put(methodName, mb);
            return mb;
        }

        private ModelTypeBuilder subclass(String subclassName) {
            this.subclassClassNames.add(subclassName);
            return this;
        }

        @Override
        protected ModelType<?> build() {
            if (builtType != null) {
                return builtType;
            }
            ModelMethod[] builtMethods = methods.values().stream()
                    .map((m) -> m.build()).toArray((i) -> new ModelMethod[i]);
            ModelField[] builtFields = fields.values().stream()
                    .map((f) -> f.build()).toArray((i) -> new ModelField[i]);
            ModelType[] builtSubs = subclassClassNames.stream()
                    .map((s) -> types.get(s).build()).toArray((i) -> new ModelType[i]);
            builtType = new ModelType(typeClass,
                    new LazyClassLoaderResolver(typeClass),
                    this.annotations.toArray(new ModelAnnotation[this.annotations.size()]),
                    builtMethods, builtFields, builtSubs);
            return builtType;
        }

    }

    private class ModelMethodBuilder extends ModelElementBuilder<ModelMethod> {

        private final ModelTypeBuilder declaringType;
        private final String methodName;
        private final Map<String, ModelParameterBuilder> parameters
                = new HashMap<>();  //key is param name
        private final Set<String> exceptions = new HashSet<>();
        private String returnType;
        private ModelMethod builtMethod;

        public ModelMethodBuilder(ModelTypeBuilder declaringType, String methodName) {
            this.declaringType = declaringType;
            this.methodName = methodName;
        }

        @Override
        ModelMethodBuilder methodContext() throws IllegalStateException {
            return this;
        }

        @Override
        ModelTypeBuilder typeContext() throws IllegalStateException {
            return declaringType;
        }

        private ModelParameterBuilder parameter(String name, String className) {
            if (parameters.containsKey(name)
                    && parameters.get(name).className
                    .contentEquals(className)) {
                return parameters.get(name);
            }
            ModelParameterBuilder pb
                    = new ModelParameterBuilder(declaringType, this,
                            name, className);
            parameters.put(name, pb);
            return pb;
        }

        private ModelMethodBuilder exception(String className) {
            types.get(
                    this.exceptions.add(className);
            return this;
        }

        private ModelMethodBuilder returnType(String className) {
            this.returnType = className;
            return this;
        }

        protected ModelMethod build() {
            if (builtMethod != null) {
                return builtMethod;
            }
            ModelParameter[] builtParams = parameters.values().stream()
                    .map((p) -> p.build())
                    .toArray((i) -> new ModelParameter[i]);
            ModelTypeUse[] builtExceptions = exceptions.stream()
                    .map((e) -> types.get(e).build())
                    .map((t) -> new ModelException(t, null, new ModelAnnotation[0], t.getType()))
                    .toArray((i) -> new ModelTypeUse[i]);
            ModelTypeUse builtReturn = (this.returnType != null)
                    ? new ModelReturn(types.get(this.returnType).build(),
                            null,
                            new ModelAnnotation[0],
                            new LazyClassLoaderResolver(this.returnType))
                    : null;
            builtMethod = new ModelMethod(de)
        }
    }

    private class ModelFieldBuilder extends ModelElementBuilder<ModelField> {

        private final ModelTypeBuilder declaringType;
        private final String fieldName;
        private final String fieldClass;
        private ModelField builtField;

        public ModelFieldBuilder(ModelTypeBuilder declaringType, String fieldName, String fieldClass) {
            this.declaringType = declaringType;
            this.fieldName = fieldName;
            this.fieldClass = fieldClass;
        }

        @Override
        ModelMethodBuilder methodContext() throws IllegalStateException {
            throw new IllegalStateException("Cannot swith to method context "
                    + "from field.");
        }

        @Override
        ModelTypeBuilder typeContext() throws IllegalStateException {
            return declaringType;
        }

        @Override
        ModelField build() {
            if (builtField != null) {
                return builtField;
            }
        }

    }

    private class ModelParameterBuilder extends ModelElementBuilder<ModelParameter> {

        private final ModelTypeBuilder declaringType;
        private final ModelMethodBuilder declaringMethod;
        private final String paramName;
        private final String className;
        private ModelParameter built;

        public ModelParameterBuilder(ModelTypeBuilder declaringType,
                ModelMethodBuilder declaringMethod,
                String paramName, String className) {
            this.declaringType = declaringType;
            this.declaringMethod = declaringMethod;
            this.paramName = paramName;
            this.className = className;
        }

        @Override
        ModelMethodBuilder methodContext() throws IllegalStateException {
            return declaringMethod;
        }

        @Override
        ModelTypeBuilder typeContext() throws IllegalStateException {
            return declaringType;
        }

        @Override
        ModelParameter build() {
            if (built != null) {
                return built;
            }
        }

    }

}
