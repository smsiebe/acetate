package org.geoint.acetate;

import org.geoint.acetate.metamodel.MutableModel;
import org.geoint.acetate.spi.model.ModelProvider;

/**
 * Simple user interface for most data binding operations.
 */
public final class Acetate {

    private static ModelProvider defaultModelProvider;

    public synchronized static void setDefaultModelProvider(ModelProvider mp) {
        defaultModelProvider = mp;
    }

    /**
     * Uses the default model provider to create a base data model from which
     * changes can be made programmatically.
     *
     * @param <T> root class type of the model
     * @param clazz root class of the model
     * @return mutable data model
     */
    public static <T> MutableModel<T> mutableModel(Class<T> clazz) {
        return defaultModelProvider.mutableModel(clazz);
    }
    

}
