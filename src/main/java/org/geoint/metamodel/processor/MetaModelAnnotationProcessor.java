package org.geoint.metamodel.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.StandardLocation;
import org.geoint.metamodel.MetaModel;
import org.geoint.metamodel.ModelRegistry;
import org.geoint.metamodel.xml.ModelDescriptor;

/**
 * Annotation processor which discovers meta-model annotations annotated with
 * {@link MetaModel} and writes the model (those classes/methods annotated with
 * meta-model annotations) as an XML descriptor file at compile time.
 */
@SupportedAnnotationTypes("org.geoint.acetate.model.meta.Meta")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MetaModelAnnotationProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv) {

        final ModelRegistry.Builder registryBuilder = ModelRegistry.builder();

        annotations.stream()
                .filter((a) -> a.getAnnotation(MetaModel.class) != null)
                .forEach((a) -> {
                    roundEnv.getElementsAnnotatedWith(a).stream()
                    .forEach((e) -> {
                        switch (e.getKind()) {
                            case METHOD:
                            case INTERFACE:
                            case CLASS:
                                registryBuilder.model(a, e);
                                break;
                        }
                    });
                });

        ModelRegistry registry = registryBuilder.build();
        if (!registry.isEmpty()) {
            //there were meta-model annotations found, load previous descriptor 
            //(if available) and merge
            ModelRegistry existingRegistry = ModelDescriptor.loadRegistry();
            ModelRegistry mergedRegistry = existingRegistry.merge(registry);
            if (mergedRegistry.getNumModels() < registry.getNumModels()) {
                //additional models were found, overwrite descriptor with merged 
                //registry
                writeDescriptor(mergedRegistry);
            }
        }

        return false;
    }

    private void writeDescriptor(ModelRegistry registry) {
        try (OutputStream out = filer.createResource(
                StandardLocation.CLASS_OUTPUT, "",
                ModelDescriptor.MODEL_DESCRIPTOR_JAR_PATH).openOutputStream()) {
            ModelDescriptor.write(registry, out);
        } catch (IOException ex) {
            messager.printMessage(Diagnostic.Kind.ERROR,
                    String.format("Problems when writing acetate model "
                            + "descriptors: '%s$'", ex.toString()));
        }
    }

}
