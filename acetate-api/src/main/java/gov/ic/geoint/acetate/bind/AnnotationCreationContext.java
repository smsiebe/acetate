package gov.ic.geoint.acetate.bind;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Context defined by an annotation annotated with, or contains, a
 * {@link DataContextAnnotation}.
 */
public class AnnotationCreationContext implements CreationContext {

    private final Annotation annotation;
    private final DataContextAnnotation dataContext;

    public AnnotationCreationContext(Annotation annotation)
            throws DataBindException {
        this.annotation = annotation;

        //check if the annotation is annotated with context
        DataContextAnnotation context = annotation.getClass()
                .getAnnotation(DataContextAnnotation.class);

        if (context == null) {
            //not annotated with context, see if one of the annotation field
            //is the context
            for (Method m : annotation.getClass().getMethods()) {
                if (m.getReturnType().equals(DataContextAnnotation.class)) {
                    try {
                        context = (DataContextAnnotation) m.invoke(annotation);
                    } catch (IllegalAccessException | IllegalArgumentException |
                            InvocationTargetException ex) {
                        throw new DataBindException(annotation.getClass(),
                                "Could not retrieve annotation creation context "
                                + "from the annotation method '"
                                + m.getName() + "'", ex);
                    }
                    break;
                }
            }
        }

        if (context == null) {
            throw new DataBindException(annotation.getClass(), "Could not find "
                    + "the DataContextAnnotation on '"
                    + annotation.getClass().getName()
                    + "'; unable to create instance without context.");
        }
        this.dataContext = context;
    }

    @Override
    public Class<?> getType() {
        return dataContext.type();
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public DataContextAnnotation getDataContext() {
        return dataContext;
    }

}
