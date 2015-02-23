package org.geoint.acetate.impl.model.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.bind.BoundData;
import org.geoint.acetate.data.annotation.Operation;
import org.geoint.acetate.metamodel.DataModel;
import org.geoint.acetate.metamodel.FieldAccessor;
import org.geoint.acetate.metamodel.FieldModel;

/**
 * Model generation using reflection.
 *
 * Reflection isn't the most sexiest approach, but it's a simple solution that
 * has pretty good performance right out of the box (no dependencies).
 *
 * @param <F> data item type
 */
public class ReflectionDataModel<F> implements DataModel<F> {

    /*
     * key: field path
     * value: field model
     */
    private Map<String, ReflectionFieldModel<?, ?, ?>> fields;
    private static final Logger logger = Logger.getLogger(ReflectionDataModel.class.getName());

    /**
     * Returns the data model straight from the class definition and acetate
     * annotations.
     *
     * @param <F> data item type
     * @param type data item type
     * @return data model for the data item type
     */
    public static <F> ReflectionDataModel<F> from(Class<F> type) {

    }

    @Override
    public BoundData<F> bind(F instance) {

    }

    /*
     * Returns field accessors and mutators in a single pass over the class.
     */
    private FieldModel[] findFields(Class<?> type) {
        Map<String, FieldModel> fields = new HashMap<>();
        Arrays.stream(type.getMethods()).parallel()
                .filter((m) -> ReflectionDataModel::isOperation)
                
                
    }

    /*
     * Predicated used to check if a method is annotated with the 
     * Operation annotation.
     */
    private boolean isOperation(Method m) {
        return m.getAnnotation(Operation.class) != null;
    }

    /**
     * Predicate used to determine if a method is a setter.
     *
     * @param m
     * @return
     */
    private boolean isSetter(Method m) {

    }

    /**
     * Predicate used to determine if the method is an accessor.
     *
     * @param m
     * @return
     */
    private boolean isGetter(Method m) {

    }

    public static class DefaultModelBuilder<D> implements DataModelBuilder<D> {

        private Class<D> modelType;

        public DefaultModelBuilder() {
        }

        public DefaultModelBuilder forType(Class<D> modelType) {
            this.modelType = modelType;
        }

        /**
         * Creates a builder by reflecting on the provided model class and
         * retrieving acetate field information.
         *
         * @param <F>
         * @param modelFrom
         * @return
         */
        public static <F> DefaultModelBuilder<F> modelFrom(Class<F> modelFrom) {
            DefaultModelBuilder mb = new DefaultModelBuilder();
            mb.forType(modelFrom);

            //first we look for accessor methods
            for (Method m : modelFrom.getMethods()) {

                //all acetate field methods must be public methods and cannot
                //be annotated with Operation
                if (!Modifier.isPublic(m.getModifiers())
                        || m.getAnnotation(Operation.class) != null) {
                    continue;
                }

                //determine if this method could be an accessor or setter
                final String fieldName;
                if (!m.getReturnType().equals(Void.class)
                        && m.getParameterCount() == 1) {
                    //looks like a setter
                    fieldName = setterFieldName(m);
                } else if (m.getParameterCount() == 0) {
                    //looks like an accessor
                    fieldName = accessorFieldName(m);
                    mb.withAccessor(fieldName, (d) -> {
                        try {
                            return m.invoke(d);
                        } catch (IllegalAccessException |
                                IllegalArgumentException |
                                InvocationTargetException ex) {
                            logger.log(Level.WARNING, "Unable to retrieve field "
                                    + "value for {0}#{1}",
                                    new Object[]{modelFrom.getName(), fieldName});
                            return null;
                        }
                    });
                }
            }
        }

        public ReflectionDataModel<D> withAccessor(String fieldName,
                FieldAccessor<D, ?> accessor) {

        }

        public ReflectionDataModel<D> build() {

        }

        private static String accessorFieldName(Method m) {

        }

        private static String setterFieldName(Method m) {

        }
    }
}
