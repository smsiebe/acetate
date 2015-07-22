package org.geoint.acetate.model.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.acetate.model.ImmutableModelRegistry;
import org.geoint.acetate.model.ModelElement;
import org.geoint.acetate.model.ModelType;
import org.geoint.acetate.model.provider.ModelProvider;

/**
 * Provides model elements described within model descriptor files.
 *
 * This provider is registered with a ServiceLoader.
 *
 * @see ModelXmlDescriptor
 * @see ServiceLoader
 */
public class ManifestModelDescriptorProvider implements ModelProvider {

    private URL[] descriptorUrls;
    private ImmutableModelRegistry modelCache;

    private static final Logger logger
            = Logger.getLogger(ManifestModelDescriptorProvider.class.getName());

    /**
     * Construct a model provider that reads from all the acetate model
     * descriptors the {@link ModelXmlDescriptor#MODEL_DESCRIPTOR_RELATIVE_PATH}
     * model descriptor path, if available.
     */
    public ManifestModelDescriptorProvider() {
        try {
            this.descriptorUrls = Collections.list(ManifestModelDescriptorProvider.class
                    .getClassLoader()
                    .getResources(ModelXmlDescriptor.MODEL_DESCRIPTOR_RELATIVE_PATH))
                    .stream()
                    .toArray((i) -> new URL[i]);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Unable to load acetate model descriptors",
                    ex);
        }
    }

    /**
     * Construct a model provider which reads from the specified model
     * descriptor source(s).
     *
     * @param urls descriptor source(s)
     */
    public ManifestModelDescriptorProvider(URL... urls) {
        this.descriptorUrls = urls;
    }

    /**
     * Construct a model provider which reads from the specified model
     * descriptor source file(s).
     *
     * @param files descriptor source(s)
     */
    public ManifestModelDescriptorProvider(File... files) {
        this(Arrays.stream(files)
                .map((f) -> f.toURI())
                .map((u) -> {
                    try {
                        return u.toURL();
                    } catch (MalformedURLException ex) {
                        assert false : "Unexpectidly received a "
                        + "MalformedURLException when convering a file URI to "
                        + "URL";
                        logger.log(Level.SEVERE, String.format(
                                        "Unable to load from acetate model "
                                        + "descriptor %s$, skipping resource; "
                                        + "model may be incomplete",
                                        u.toString()), ex);
                        return null;
                    }
                })
                //filter out nulls caused by MalformedURLException above, 
                //which should never happen
                .filter(Objects::nonNull)
                .toArray((i) -> new URL[i])
        );
    }

    /**
     * Reads the model descriptor and returns the individual model elements from
     * the descriptor.
     *
     * @return model elements from the descriptor
     */
    @Override
    public Collection<ModelElement> getModelElements() {

        synchronized (this) {
            if (modelCache == null) {
                modelCache = ImmutableModelRegistry.register(loadFromDescriptors());
            }
        }
        return modelCache.getModels();
    }

    public void reload() {
        synchronized (this) {
            modelCache = ImmutableModelRegistry.register(loadFromDescriptors());
        }
    }

    private Collection<ModelElement> loadFromDescriptors() {

        return Arrays.stream(descriptorUrls)
                .parallel()
                .map((u) -> {
                    try {
                        return loadFromDescriptor(u);
                    } catch (IOException ex) {
                        //skip and log vs throw exception/terminate operation 
                        //since other ModelProvier instances may provide coverage 
                        logger.log(Level.SEVERE, String.format("Unable to read from "
                                        + "acetate model descriptor '%s$', skipping resource; "
                                        + "model may be incomplete",
                                        u.toString()),
                                ex);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private ModelType<?> loadFromDescriptor(URL url) throws IOException {
        //load the ModelType description from the descriptor file
        try (InputStream in = url.openStream()) {
            return ModelXmlDescriptor.read(in);
        }
    }
//
//    /**
//     * Adds all models related to this model type to the provided collection.
//     *
//     * @param typeModel
//     * @param models collection where extracted models are added
//     * @return all models related to this type
//     */
//    private void extractTypeModels(ModelType typeModel,
//            Collection<ModelElement> models) {
//
//        //self
//        models.add(typeModel);
//
//        //type model annotations
//        extractModels(models, this::extractModelAnnotations, typeModel);
//
//        //fields
//        Arrays.stream(typeModel.getModelFields())
//                .forEach((f) -> {
//                    extractModels(models, this::extractUseModel, f);
//                });
//
//        //methods
//        Arrays.stream(typeModel.getModelMethods())
//                .forEach((m) -> {
//                    extractModelAnnotationsTo(m, models);
//                    models.add(m);
//                    models.addAll(m.getParameters());
//                    models.addAll(m.getExceptions());
//                    if (m.getReturnType() != null) {
//                        models.add(m.getReturnType());
//                    }
//                });
//        
//    }
//    
//    private void extractModelAnnotations(ModelElement model,
//            final Collection<ModelElement> models) {
//        
//    }
//    
//    private void extractUseModel(ModelTypeUse useModel,
//            final Collection<ModelElement> models) {
//
//        //
//    }
//    
//    private <M extends ModelElement> void extractModels(
//            Collection<ModelElement> extractionDestination,
//            ModelExtractor<M> extractor,
//            M... models) {
//        Arrays.stream(models)
//                .forEach((m) -> extractor.extract(m, extractionDestination));
//        
//    }
//    
//    @FunctionalInterface
//    private interface ModelExtractor<M extends ModelElement> {
//        
//        void extract(M model, Collection<ModelElement> extractTo);
//    }
}
