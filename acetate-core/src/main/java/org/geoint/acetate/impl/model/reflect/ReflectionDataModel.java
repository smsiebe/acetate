package org.geoint.acetate.impl.model.reflect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.acetate.bind.object.annotation.Operation;
import org.geoint.acetate.model.ClassModel;
import org.geoint.acetate.model.ComponentModel;
import org.geoint.acetate.model.ValueConstraint;
import org.geoint.acetate.model.ValueConstraintException;
import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.model.ValueModel;
import org.geoint.acetate.spi.model.DataType;
import org.geoint.extension.Providers;

/**
 * Class DataModel generation using reflection.
 *
 * @param <T> model type
 */
public class ReflectionDataModel<T> implements DataModel<T> {

    private final String id;
    private final ValueModel<?> guid;
    private final Optional<ValueModel<?>> version;
    private final ValueConstraint<T>[] constraints;
    private final Map<String, ComponentModel<?>> components;

    //simple on-heap cache for "vanilla" class models
    private static final Map<Class<?>, ClassModel<?>> modelCache
            = Collections.synchronizedMap(new WeakHashMap<>());
    //load data types extensions once
    private static final Map<Class<?>, DataType<?>> dataTypes
            = Providers.getDefault().findExtensions(DataType.class).stream()
            .collect(
                    Collectors.toMap(
                            (e) -> e.getExtension().getType(),
                            (e) -> e.getExtension()
                    )
            );
    private static final Logger logger
            = Logger.getLogger(ReflectionDataModel.class.getName());

    private ReflectionDataModel(String id, ValueModel<?> guid,
            Optional<ValueModel<?>> version,
            ValueConstraint<T>[] constraints,
            Map<String, ComponentModel<?>> components) {
        this.id = id;
        this.guid = guid;
        this.version = version;
        this.constraints = constraints;
        this.components = components;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ValueModel<?> getGUID() {
        return guid;
    }

    @Override
    public Optional<ValueModel<?>> getVersion() {
        return version;
    }

    @Override
    public Collection<ValueConstraint<T>> getConstraints() {
        return Arrays.asList(constraints);
    }

    @Override
    public void validate(T data) throws ValueConstraintException {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Create a DataModel for a Java class using reflection.
     *
     * @param <T> class type
     * @param type class to model
     * @return DataModel for class
     * @throws ModelException if there were any problems creating the meta model
     */
    public static <T> DataModel<T> model(Class<T> type)
            throws ModelException {
        ClassModel<T> rootType = model(type);

    }

    /**
     * Recursively reflect on the provided type, returning the model.
     *
     * @param <T> class type
     * @param type class type
     * @return data model for class
     */
    private static <T> ComponentModel<T> reflect(Class<T> type) {

    }

    private static boolean isAccessor(Method m) {
        return !m.getReturnType().equals(Void.class
        )
                && m.getParameterCount() == 0;
    }

    private static boolean isMutator(Method m) {
        return m.getReturnType().equals(Void.class
        )
                && m.getParameterCount() == 1;
    }
    /*
     * Predicated used to check if a method is annotated with the 
     * Operation annotation.
     */

    private static boolean isOperation(Method m) {
        return m.getAnnotation(Operation.class
        ) != null;
    }

}
