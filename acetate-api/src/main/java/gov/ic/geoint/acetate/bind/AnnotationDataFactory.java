package gov.ic.geoint.acetate.bind;

import java.lang.annotation.Annotation;

/**
 * Constructs a data instance based on the provided annotation.
 *
 * @param <T> composed data type
 */
@FunctionalInterface
public interface AnnotationDataFactory<T> extends DataFactory<Annotation[], T> {

    /**
     * Returns an instance from the provided annotation data.
     *
     * @param annotations annotation context
     * @return instance of the data
     * @throws DataBindException thrown if there are any problems
     * instantiating the data
     */
    @Override
    T getInstance(Annotation... annotations) throws DataBindException;

}
