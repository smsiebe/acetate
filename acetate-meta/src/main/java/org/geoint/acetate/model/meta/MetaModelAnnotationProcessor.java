package org.geoint.acetate.model.meta;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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
import org.geoint.acetate.model.ModelType;
import org.geoint.acetate.model.xml.ModelXmlDescriptor;

/**
 * Annotation processor which discovers model elements annotated with
 * {@link Meta} and writes the resultant model as an XML descriptor files at
 * compile time.
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

        //grab model types annotated with the metamodel annotation
        List<ModelType> types = annotations.parallelStream()
                .filter((a) -> a.getAnnotation(Meta.class) != null)
                .flatMap((a) -> roundEnv.getElementsAnnotatedWith(a).stream())
                .parallel()
                .map(this::modelType)
                .filter(Objects::nonNull) //returns null if type wasn't class or interface
                .collect(Collectors.toList());

        try {
            //types include all member and hierarchy information already, so just
            //save model file for each one
            writeDescriptors(types);
        } catch (IOException ex) {
            messager.printMessage(Diagnostic.Kind.ERROR,
                    String.format("Problems when writing acetate model "
                            + "descriptors: '%s$'", ex.toString()));
        }

        return false;
    }

    /**
     * Models a ElementKind.CLASS or ElementKind.INTERFACE element as a
     * ModelType, or returns null.
     *
     * @param e
     * @return model of the type or null
     */
    private ModelType modelType(Element e) {e.
        //only model types and use reflection to "fill out" model components 
        //within that type
        if (!isClassOrInterfaceType(e)) {
            return null;
        }
fdsfsd //TODO finish this using reflection
    }

    /**
     * Writes the model-specific descriptor(s) for the models.
     *
     * @param typeModels
     * @throws IOException
     */
    private void writeDescriptors(Collection<ModelType> typeModels)
            throws IOException {
        for (ModelType t : typeModels) {
            writeDescriptor(t);
        }
    }

    private void writeDescriptor(ModelType m) throws IOException {
        try (OutputStream out = filer.createResource(StandardLocation.CLASS_OUTPUT,
                "", ModelXmlDescriptor.descriptorPath(m)).openOutputStream()) {
            ModelXmlDescriptor.write(m, out);
        }
    }

    private boolean isClassOrInterfaceType(Element e) {
        final ElementKind kind = e.getKind();
        return kind.equals(ElementKind.CLASS)
                || kind.equals(ElementKind.INTERFACE);
    }
}
