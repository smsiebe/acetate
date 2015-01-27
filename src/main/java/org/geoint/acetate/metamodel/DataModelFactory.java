package org.geoint.acetate.metamodel;

/**
 * Creates a data model.
 */
public class DataModelFactory {

    /**
     * Create a blank data model builder.
     *
     * @return blank data model builder
     */
    public static DataModelBuilder<?> builder() {
        return new DefaultModelBuilder();
    }

    /**
     * Create a new data model by starting with extracting fields out of the
     * provided class.
     *
     * @param <F> object type to model from
     * @param instance object from which to create a model
     * @return builder
     */
    public static <F> DataModelBuilder<F> builder(Class<F> instance) {
        return new DefaultModelBuilder(instance.getClass());
    }

   
}
