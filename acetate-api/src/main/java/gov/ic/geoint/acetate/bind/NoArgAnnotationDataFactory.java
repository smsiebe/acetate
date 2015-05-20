package gov.ic.geoint.acetate.bind;

/**
 * Default data factory which constructs an object instance from the context of
 * an annotation.
 *
 * This factory always attempts to instantiate the instance using its no-arg
 * constructor.
 *
 */
public final class NoArgAnnotationDataFactory
        implements AnnotationDataFactory {

    @Override
    public Object getInstance(CreationContext creationContext)
            throws DataBindException {

        try {
            return creationContext.getType().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new DataBindException(creationContext.getType(), "Unable to "
                    + "create instance using a no-arg constructor.", ex);
        }
    }

}
