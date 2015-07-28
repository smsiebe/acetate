package org.geoint.metamodel.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.geoint.metamodel.ModelRegistry;

/**
 * Reads/writes the model type as an XML file.
 */
public final class ModelDescriptor {

    public static final String MODEL_DESCRIPTOR_FILENAME
            = "m3.xml";
    private static final Logger logger
            = Logger.getLogger(ModelDescriptor.class.getName());

    public static final String MODEL_DESCRIPTOR_RELATIVE_PATH
            = "META-INF/acetate-models";
    private static final String XML_DESCRIPTOR_NAMESPACE
            = "namespace.geoint.org:acetate:descriptor:v1";
    private static final String XML_ELEMENT_ROOT = "metamodel";
    private static final String XML_ELEMENT_TYPES = "types";
    private static final String XML_ELEMENT_TYPE = "type";
    private static final String XML_ELEMENT_ANNOTATIONS = "annotations";
    private static final String XML_ELEMENT_ANNOTATION = "annotation";
    private static final String XML_ELEMENT_METHODS = "methods";
    private static final String XML_ELEMENT_METHOD = "method";
    private static final String XML_ELEMENT_PARAMETERS = "parameters";
    private static final String XML_ELEMENT_PARAMETER = "parameter";
    private static final String XML_ATTRIBUTE_CLASSNAME = "className";
    private static final String XML_ATTRIBUTE_NAME = "name";

    /**
     * Writes the model descriptor xml to the output stream.
     *
     * @param registry
     * @param out
     * @throws IOException
     */
    public static void write(ModelRegistry registry, OutputStream out)
            throws IOException {
        ModelXmlDescriptorStaxWriter writer
                = new ModelXmlDescriptorStaxWriter(registry, out);
        writer.call();
    }

    /**
     * Load model information from this jars metamodel descriptor.
     *
     * @return model elements found in this jars metamodel descriptor
     */
    public static ModelRegistry loadRegistry() {
        try {
            ClassLoader loader = ModelDescriptor.class.getClassLoader();
            return loadRegistry(
                    loader.getResourceAsStream("META-INF/" + MODEL_DESCRIPTOR_FILENAME),
                    loader);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Unable to load metamodel descriptor from "
                    + "local jar", ex);
            return ModelRegistry.EMPTY;
        }
    }

    public static ModelRegistry loadRegistry(File jarFile, ClassLoader loader)
            throws IOException {
        throw new UnsupportedOperationException();
    }

    public static ModelRegistry loadRegistry(InputStream in, ClassLoader loader)
            throws IOException {
        ModelXmlDescriptorStaxReader reader
                = new ModelXmlDescriptorStaxReader(in, loader);
        return reader.call();
    }

    /*
     <metamodel>
     <annotations>
     <annotation className="...">
     <types>
     <type className="..."/>
     ...
     </types>
     <methods>
     <method name="..." className="...">
     <parameters>
     <parameter className="..."/>
     </parameters>
     ...
     </method>
     ...
     </methods>
     </annotation>
     ...
     </annotations>
     </metamodel>
     */
    public static class ModelXmlDescriptorStaxReader
            implements Callable<ModelRegistry> {

        private final InputStream in;
        private final ClassLoader classLoader;
        private final ModelRegistry.Builder builder;

        public ModelXmlDescriptorStaxReader(InputStream in, ClassLoader classLoader) {
            this.in = in;
            this.classLoader = classLoader;
            this.builder = ModelRegistry.builder();
        }

        @Override
        public ModelRegistry call() throws IOException {
            final XMLInputFactory xif = XMLInputFactory.newInstance();

            try {
                XMLStreamReader reader = xif.createXMLStreamReader(in);

                while (reader.hasNext()) {
                    reader.next();

                    if (reader.isStartElement()
                            && reader.getLocalName().contentEquals(XML_ELEMENT_ANNOTATION)) {
                        readAnnotationModel(reader);
                    }
                }
                return builder.build();
            } catch (XMLStreamException ex) {
                throw new IOException("Failed to read acetate model descriptor "
                        + "file.", ex);
            }
        }

        private void readAnnotationModel(XMLStreamReader reader)
                throws XMLStreamException {
            final String annotationClass = readClassAttribute(reader);
            try {
                Class<? extends Annotation> modelAnnotation
                        = (Class<? extends Annotation>) loadClass(annotationClass);
                do {
                    reader.next();

                    if (reader.isStartElement()) {
                        switch (reader.getLocalName()) {
                            case XML_ELEMENT_TYPE:
                                readTypeModel(reader, modelAnnotation);
                                break;
                            case XML_ELEMENT_METHOD:
                                readMethodModel(reader, modelAnnotation);
                                break;
                        }
                    }

                } while (!(reader.isEndElement()
                        && reader.getLocalName().contentEquals(XML_ELEMENT_ANNOTATION))
                        && reader.hasNext());
            } catch (ClassNotFoundException | ClassCastException ex) {
                //model annotation class not found, skip modeling everything 
                //for this  annotation
                logger.log(Level.SEVERE, String.format("ModelAnnotation '%s' "
                        + "invalid, skipping annotation model,",
                        annotationClass), ex);
                do {
                    //skipping the rest of the annotation-related models
                    reader.next();
                    if (reader.getEventType() == XMLStreamReader.END_ELEMENT
                            && reader.getLocalName().contentEquals(XML_ELEMENT_ANNOTATION)) {
                        break;
                    }
                } while (reader.hasNext());
            }
        }

        private void readTypeModel(XMLStreamReader reader,
                Class<? extends Annotation> modelAnnotation) {
            final String className = readClassAttribute(reader);
            try {
                //<type className="..."/>
                builder.model(modelAnnotation, loadClass(className));
            } catch (ClassNotFoundException ex) {
                logger.log(Level.SEVERE, String.format("'%s' was identified as type of "
                        + "metamodel '%s' in model descriptor, however could "
                        + "not find class with loader.  Skipping type.",
                        className, modelAnnotation.getTypeName()), ex);
            }
        }

        private void readMethodModel(XMLStreamReader reader,
                Class<? extends Annotation> modelAnnotation)
                throws XMLStreamException {
            final String methodName = readNameAttribute(reader);
            final String declaringClassName = readClassAttribute(reader);
            try {
                //<method name="..." className="...">
                // [<parameter className="..."/>
                // ...
                //</method>
                Class<?> declaringClass = loadClass(declaringClassName);
                declaringClass.getMethod(methodName, getParameterClasses(reader));
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
                logger.log(Level.SEVERE, String.format("Method '%s', declared "
                        + "by type '%s', was found in metamodel descriptor "
                        + "for model annotation type '%s' but "
                        + "method could not be loaded, skipping "
                        + "method.",
                        methodName, declaringClassName, modelAnnotation.getTypeName()), ex);
            }
        }

        private Class<?>[] getParameterClasses(XMLStreamReader reader)
                throws XMLStreamException, ClassNotFoundException {
            List<Class<?>> parameters = new ArrayList<>();
            do {
                reader.next();
                if (reader.isStartElement()
                        && reader.getLocalName().contentEquals(XML_ELEMENT_PARAMETER)) {
                    parameters.add(loadClass(readClassAttribute(reader)));
                }
            } while (reader.hasNext()
                    && !(reader.isEndElement() && reader.getLocalName().contentEquals(XML_ELEMENT_METHOD)));
            return parameters.toArray(new Class[parameters.size()]);
        }

        private String readNameAttribute(XMLStreamReader reader) {
            return reader.getAttributeValue(XML_DESCRIPTOR_NAMESPACE,
                    XML_ATTRIBUTE_NAME);
        }

        private String readClassAttribute(XMLStreamReader reader) {
            return reader.getAttributeValue(XML_DESCRIPTOR_NAMESPACE,
                    XML_ATTRIBUTE_CLASSNAME);
        }

        private Class<?> loadClass(String className)
                throws ClassNotFoundException {
            return Class.forName(className, true, classLoader);
        }
    }

    public static class ModelXmlDescriptorStaxWriter implements Callable<Object> {

        private final ModelRegistry registry;
        private final OutputStream out;

        public ModelXmlDescriptorStaxWriter(ModelRegistry registry, OutputStream out) {
            this.registry = registry;
            this.out = out;
        }

        @Override
        public Object call() throws IOException {
            XMLOutputFactory of = XMLOutputFactory.newInstance();
            try {
                XMLStreamWriter writer = of.createXMLStreamWriter(out);
                writer.writeStartDocument();
                writer.setDefaultNamespace(XML_DESCRIPTOR_NAMESPACE);
                writer.writeStartElement(XML_ELEMENT_ROOT);
                writeNillableArray(writer, XML_ELEMENT_ANNOTATIONS,
                        registry.getModelAnnotations(), this::writeAnnotation);
                writer.writeEndDocument();
                return null;
            } catch (XMLStreamException ex) {
                throw new IOException("Failed to complete writing acetate model "
                        + "descriptor file.", ex);
            }
        }

        private void writeAnnotation(XMLStreamWriter writer,
                Class<? extends Annotation> modelAnnotation)
                throws XMLStreamException {

            writer.writeStartElement(XML_ELEMENT_ANNOTATION);
            writer.writeAttribute(XML_ATTRIBUTE_CLASSNAME, modelAnnotation.getTypeName());

            writeNillableArray(writer, XML_ELEMENT_TYPES,
                    registry.getModelTypes(modelAnnotation), this::writeType);

            writeNillableArray(writer, XML_ELEMENT_METHODS,
                    registry.getModelMethods(modelAnnotation), this::writeMethod);

            writer.writeEndElement();
        }

        private void writeType(XMLStreamWriter writer, Class<?> type)
                throws XMLStreamException {
            writer.writeStartElement(XML_ELEMENT_TYPE);
            writer.writeAttribute(XML_ATTRIBUTE_CLASSNAME, type.getCanonicalName());
            writer.writeEndElement();
        }

        private void writeMethod(XMLStreamWriter writer, Method method) throws XMLStreamException {
            writer.writeStartElement(XML_ELEMENT_METHOD);
            writer.writeAttribute(XML_ATTRIBUTE_NAME, method.getName());
            writer.writeAttribute(XML_ATTRIBUTE_CLASSNAME,
                    method.getDeclaringClass().getCanonicalName());
            writeNillableArray(writer, XML_ELEMENT_PARAMETERS,
                    Arrays.asList(method.getParameters()), this::writeParameter);
            writer.writeEndElement();
        }

        private void writeParameter(XMLStreamWriter writer, Parameter p)
                throws XMLStreamException {
            writer.writeStartElement(XML_ELEMENT_PARAMETER);
            writer.writeAttribute(XML_ATTRIBUTE_CLASSNAME, p.getType().getCanonicalName());
            writer.writeEndElement();
        }

        private <E> void writeNillableArray(XMLStreamWriter writer,
                String arrayWrapperLocalName,
                Collection<E> elements,
                XMLStreamArrayElementWriter<E> elementWriter)
                throws XMLStreamException {
            if (!elements.isEmpty()) {
                writer.writeStartElement(arrayWrapperLocalName);
                for (E e : elements) {
                    elementWriter.write(writer, e);
                }
                writer.writeEndElement();
            }
        }

        @FunctionalInterface
        private interface XMLStreamArrayElementWriter<E> {

            void write(XMLStreamWriter writer, E element)
                    throws XMLStreamException;
        }

//        private void writeType(XMLStreamWriter writer, ModelType typeModel)
//                throws XMLStreamException, IOException {
//            writer.writeStartElement(XML_ELEMENT_TYPE);
//            writer.writeAttribute(XML_ATTRIBUTE_CLASSNAME, typeModel.getTypeName());
//            writeNillableArray(writer, typeModel.getModelAnnotations(),
//                    XML_ELEMENT_ANNOTATIONS, this::writeAnnotation);
//            writeNillableArray(writer, typeModel.getModelFields(),
//                    XML_ELEMENT_FIELDS, this::writeField);
//            writeNillableArray(writer, typeModel.getModelMethods(),
//                    XML_ELEMENT_METHODS, this::writeMethod);
//            writer.writeEndElement();
//        }
//
//        private void writeMethod(XMLStreamWriter writer, ModelMethod m)
//                throws XMLStreamException {
//            writer.writeStartElement(XML_ELEMENT_METHOD);
//            writer.writeAttribute(XML_ATTRIBUTE_NAME, m.getName());
//            writeNillableArray(writer, m.getModelAnnotations(),
//                    XML_ELEMENT_ANNOTATIONS, this::writeAnnotation);
//            writeNillableArray(writer, m.getParameters(),
//                    XML_ELEMENT_PARAMETERS, this::writeParameter);
//            writeNillableArray(writer, m.getExceptions(),
//                    XML_ELEMENT_EXCEPTIONS, this::writeException);
//            if (m.getReturnType() != null) {
//                writeTypeUse(writer, m.getReturnType(), XML_ELEMENT_RETURN, null);
//            }
//            writer.writeEndElement();
//        }
//        private void writeException(XMLStreamWriter writer,
//                ModelTypeUse<? extends Throwable> ex) throws XMLStreamException {
//            writeTypeUse(writer, ex, XML_ELEMENT_EXCEPTION, null);
//        }
//
//        private void writeParameter(XMLStreamWriter writer, ModelParameter p)
//                throws XMLStreamException {
//            writeTypeUse(writer, p, XML_ELEMENT_PARAMETER,
//                    ModelParameter::getName);
//        }
//
//        private void writeField(XMLStreamWriter writer, ModelField f) throws XMLStreamException {
//            writeTypeUse(writer, f, XML_ELEMENT_FIELD, ModelField::getName);
//        }
//
//        private <M extends ModelTypeUse> void writeTypeUse(
//                XMLStreamWriter writer, M model,
//                String elementName,
//                Function<M, String> localNameFunction)
//                throws XMLStreamException {
//            writer.writeStartElement(elementName);
//            if (localNameFunction != null) {
//                writer.writeAttribute(XML_ATTRIBUTE_NAME, localNameFunction.apply(model));
//            }
//            writer.writeAttribute(XML_ATTRIBUTE_CLASSNAME, model.getType().getTypeName());
//            writeNillableArray(writer, model.getUseModelAnnotations(),
//                    XML_ELEMENT_ANNOTATIONS, this::writeAnnotation);
//            writer.writeEndElement();
//        }
//
//        private void writeAnnotation(XMLStreamWriter writer,
//                ModelAnnotation a) throws XMLStreamException {
//            writer.writeStartElement(XML_ELEMENT_MODEL_ANNOTATION);
//            writer.writeCharacters(a.getAnnotationName());
//            writer.writeEndElement();
//        }
//
//        private <M extends ModelElement> void writeNillableArray(
//                XMLStreamWriter writer, M[] models,
//                String wrapperElementName, XMLStreamWriterConsumer<M> itemOperation)
//                throws XMLStreamException {
//            if (models.length == 0) {
//                return;
//            }
//
//            writer.writeStartElement(wrapperElementName);
//            for (M m : models) {
//                itemOperation.write(writer, m);
//            }
//            writer.writeEndElement();
//        }
//
//        @FunctionalInterface
//        private interface XMLStreamWriterConsumer<M extends ModelElement> {
//
//            void write(XMLStreamWriter writer, M model) throws XMLStreamException;
//        }
    }

}
