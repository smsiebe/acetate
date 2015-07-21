package org.geoint.acetate.model.xml;

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import org.geoint.acetate.model.meta.Meta;

/**
 * Annotation processor which discovers model elements annotated with
 * {@link Meta} and writes the resultant model as an XML descriptor file within
 * the jars META-INF directory.
 * <p>
 * The location of the model XML descriptor is defined as a MANIFEST.mf
 * property.
 */
@SupportedAnnotationTypes({"org.geoint.acetate.model.meta.Meta"})
public class MetaModelAnnotationProcessor extends AbstractProcessor {

    public static final String MANIFEST_XML_FILE_PROPERTY_NAME
            = "acetate.metamodel.xml.file";
//    private Filer filer;
//    private Messager messager;
    
    
//    @Override
//    public void init (ProcessingEnvironment processingEnv) {
//        super.init(processingEnv);
//        this.filer = processingEnv.getFiler();
//        this.messager = processingEnv.getMessager();
//    }
//    
    @Override
    public boolean process(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv) {
        
//        annotations.stream()
//                .filter((a) -> a.getAnnotation(Meta.class) != null) 
//                .flatMap((a) -> roundEnv.getElementsAnnotatedWith(Meta.class).stream())
//                .forEach((e) -> 
//                        messager.printMessage(Diagnostic.Kind.NOTE, 
//                                "Found model: "+e.getSimpleName().toString()));
        return true;
    }

    
}
