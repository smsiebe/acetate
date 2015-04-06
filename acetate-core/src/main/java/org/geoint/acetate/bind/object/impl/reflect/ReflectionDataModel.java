package org.geoint.acetate.bind.object.impl.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.bind.BoundData;
import org.geoint.acetate.data.annotation.Operation;
import org.geoint.acetate.metamodel.DataModel;
import org.geoint.acetate.metamodel.FieldAccessor;
import org.geoint.acetate.metamodel.FieldModel;
import org.geoint.acetate.metamodel.FieldSetter;

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
     * key: field name
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
        Map<String, FieldAccessor> accessors = new HashMap<>();
        Map<String, FieldSetter> setters = new HashMap<>();
        List<String> fieldNames = new ArrayList<>();
        
        //single pass over the class methods
        Arrays.stream(type.getMethods()).parallel()
                .forEach((m) -> {
                    if (isOperation(m)) {
                        return;
                    }
                    //determine if this method could be an accessor or setter
                    String fieldName = null;
                    if (m.getReturnType().equals(Void.class)
                    && m.getParameterCount() == 1) {
                        //looks like a setter
                        fieldName = setterFieldName(m);
                        if (setters.containsKey(fieldName)) {
                            logger.log(Level.WARNING, "Found multiple setters "
                                    + "for field '{0}' in type '{1}'.",
                                    new Object[]{
                                        fieldName,
                                        type.getClass().getName()
                                    });
                        }
                        setters.put(fieldName, createSetter(fieldName, m));
                    } else if (!m.getReturnType().equals(Void.class)
                    && m.getParameterCount() == 0) {
                        //looks like an accessor
                       fieldName = accessorFieldName(m);
                        if (accessors.containsKey(fieldName)) {
                            logger.log(Level.WARNING, "Found multiple accessors "
                                    + "for field '{0}' in type '{1}'.",
                                    new Object[]{
                                        fieldName,
                                        type.getClass().getName()
                                    });
                        }
                        accessors.put(fieldName, createAccessor(fieldName, m));
                    }
                    
                    if (fieldName != null 
                            && !fieldNames.contains(fieldName)) {
                        fieldNames.add(fieldName);
                    }
                });
        
        FieldModel[] fields = new FieldModel[fieldNames.size()];
        for (int i=0;i<fieldNames.size();i++) {
            final String fn = fieldNames.get(i);
            fields[i] = fieldModel(accessors.get(fn), setters.get(fn));
        }
        
        return fields;        
    }

    private FieldModel<?,?> fieldModel(FieldAccessor<?,?> accessor,
            FieldSetter<?,?> setter) {
        
    }
    
    private FieldSetter<?, ?> createSetter(String fieldName, Method m) {

    }

    private FieldAccessor<?, ?> createAccessor(String fieldName, Method m) {

    }

    private String accessorFieldName(Method m) {

    }

    private String setterFieldName(Method m) {

    }
    /*
     * Predicated used to check if a method is annotated with the 
     * Operation annotation.
     */

    private boolean isOperation(Method m) {
        return m.getAnnotation(Operation.class) != null;
    }

}
