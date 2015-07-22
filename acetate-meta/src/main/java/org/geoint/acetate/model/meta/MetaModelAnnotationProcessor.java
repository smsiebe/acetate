package org.geoint.acetate.model.meta;

import java.util.List;
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
import org.geoint.acetate.model.ModelElement;
import org.geoint.acetate.model.ModelType;

/**
 * Annotation processor which discovers model elements annotated with
 * {@link Meta} and writes the resultant model as an XML descriptor file within
 * the jars META-INF directory.
 * <p>
 * The location of the model XML descriptor is defined as a MANIFEST.mf
 * property.
 */
@SupportedAnnotationTypes("org.geoint.acetate.model.meta.Meta")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MetaModelAnnotationProcessor extends AbstractProcessor {

    public static final String MANIFEST_XML_FILE_PROPERTY_NAME
            = "acetate.metamodel.xml.file";
    private Filer filer;
    private Messager messager;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        //grab model types annotated with the metamodel annotation
        List<ModelType> types = annotations.stream()
                .filter((a) -> a.getAnnotation(Meta.class) != null)
                .flatMap((a) -> roundEnv.getElementsAnnotatedWith(a).stream())
                //                .forEach((e) -> {
                //                    try {
                //                        FileObject obj = this.filer.createResource(StandardLocation.CLASS_OUTPUT,
                //                                "",
                //                                "resources/"+e.getSimpleName());
                //                        try (Writer out = obj.openWriter()) {
                //                            out.write("test");
                //                        }
                //
                //                    } catch (IOException ex) {
                //                        Logger.getLogger(MetaModelAnnotationProcessor.class.getName()).log(Level.SEVERE, null, ex);
                //                    }
                //                });
                
                //only care about class/interfaces, we'll use reflection 
                //to get member annotations
                .filter((e) -> e.getKind().equals(ElementKind.CLASS)
                        || e.getKind().equals(ElementKind.INTERFACE)) 
                .parallel()
                .map(this::model)
                .map((m) -> (ModelType) m) //since we previously filter class/interfaces
                .collect(Collectors.toList());
                
        //types include all member and hierarchy information already, so just
        //save model file for each one
        return false;
    }

    private ModelElement model(Element e) {

    }
}
