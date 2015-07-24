package org.geoint.acetate.model.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.geoint.acetate.model.ModelAnnotation;
import org.geoint.acetate.model.ModelBuilder;
import org.geoint.acetate.model.ModelElement;
import org.geoint.acetate.model.ModelField;
import org.geoint.acetate.model.ModelMethod;
import org.geoint.acetate.model.ModelParameter;
import org.geoint.acetate.model.ModelType;
import org.geoint.acetate.model.ModelTypeUse;

/**
 * Reads/writes the model type as an XML file.
 */
public final class ModelXmlDescriptor {

    public static final String MODEL_DESCRIPTOR_RELATIVE_PATH
            = "META-INF/acetate-models";
    private static final Logger logger
            = Logger.getLogger(ModelXmlDescriptor.class.getName());

    private static final String XML_DESCRIPTOR_NAMESPACE
            = "namespace.geoint.org:acetate:descriptor:v1";
    private static final String XML_ELEMENT_ROOT = "metamodel";
    private static final String XML_ELEMENT_TYPE = "type";
    private static final String XML_ELEMENT_ANNOTATIONS = "annotations";
    private static final String XML_ELEMENT_MODEL_ANNOTATION = "modelAnnotation";
    private static final String XML_ELEMENT_FIELDS = "fields";
    private static final String XML_ELEMENT_FIELD = "field";
    private static final String XML_ELEMENT_METHODS = "methods";
    private static final String XML_ELEMENT_METHOD = "method";
    private static final String XML_ELEMENT_PARAMETERS = "parameters";
    private static final String XML_ELEMENT_PARAMETER = "parameter";
    private static final String XML_ELEMENT_EXCEPTIONS = "exceptions";
    private static final String XML_ELEMENT_EXCEPTION = "exception";
    private static final String XML_ELEMENT_RETURN = "returnType";
    private static final String XML_ATTRIBUTE_CLASSNAME = "className";
    private static final String XML_ATTRIBUTE_NAME = "name";

    /**
     * Writes the model descriptor xml to the output stream.
     *
     * @param typeModel
     * @param out
     * @throws IOException
     */
    public static void write(ModelType typeModel, OutputStream out)
            throws IOException {
        ModelXmlDescriptorStaxWriter writer
                = new ModelXmlDescriptorStaxWriter(typeModel, out);
        writer.call();
    }

    /**
     * Returns the ModelType from the xml descriptor.
     *
     * @param in
     * @return type model
     * @throws IOException
     */
    public static ModelType read(InputStream in)
            throws IOException {
        ModelXmlDescriptorStaxReader reader
                = new ModelXmlDescriptorStaxReader(in);
        return reader.call();
    }

    /**
     * Returns the relative model descriptor path for the model type.
     *
     * @param typeModel
     * @return
     */
    public static String descriptorPath(ModelType typeModel) {
        return String.join("/", MODEL_DESCRIPTOR_RELATIVE_PATH,
                typeModel.getTypeName());
    }

    public static class ModelXmlDescriptorStaxReader implements Callable<ModelType> {

        private final InputStream in;

        public ModelXmlDescriptorStaxReader(InputStream in) {
            this.in = in;
        }

        @Override
        public ModelType call() throws IOException {
            final XMLInputFactory xif = XMLInputFactory.newInstance();
            ModelBuilder builder = new ModelBuilder();
            try {
                XMLStreamReader reader = xif.createXMLStreamReader(in);
                while (reader.hasNext()) {
                    reader.next();

                    switch (reader.getEventType()) {
                        case XMLStreamReader.START_ELEMENT:
                            switch (reader.getLocalName()) {
                                case XML_ELEMENT_TYPE:
                                    builder.type(reader.getAttributeValue(
                                            XML_DESCRIPTOR_NAMESPACE, XML_ATTRIBUTE_CLASSNAME));
                                    break;
                                case XML_ELEMENT_FIELD:
                                    builder.field(readNameAttribute(reader),
                                            readClassAttribute(reader));
                                    break;
                                case XML_ELEMENT_METHOD:
                                    builder.method(readNameAttribute(reader));
                                    break;
                                case XML_ELEMENT_PARAMETER:
                                    builder.methodParameter(readNameAttribute(reader),
                                            readClassAttribute(reader));
                                    break;
                                case XML_ELEMENT_EXCEPTION:
                                    builder.methodException(readClassAttribute(reader));
                                    break;
                                case XML_ELEMENT_RETURN:
                                    builder.methodReturn(readClassAttribute(reader));
                                    break;
                                case XML_ELEMENT_MODEL_ANNOTATION:
                                    builder.annotation(reader.getElementText());
                            }
                            break;
                    }

                }
            } catch (XMLStreamException ex) {
                throw new IOException("Failed to read acetate model descriptor "
                        + "file.", ex);
            }
            ModelType[] modelTypes = builder.build();
            if (modelTypes.length == 0) {
                throw new IOException("No models found.");
            }
            return modelTypes[0];
        }

        private String readNameAttribute(XMLStreamReader reader) {
            return reader.getAttributeValue(XML_DESCRIPTOR_NAMESPACE,
                    XML_ATTRIBUTE_NAME);
        }

        private String readClassAttribute(XMLStreamReader reader) {
            return reader.getAttributeValue(XML_DESCRIPTOR_NAMESPACE,
                    XML_ATTRIBUTE_CLASSNAME);
        }
    }

    public static class ModelXmlDescriptorStaxWriter implements Callable<Object> {

        private final ModelType<?> typeModel;
        private final OutputStream out;

        public ModelXmlDescriptorStaxWriter(ModelType<?> typeModel, OutputStream out) {
            this.typeModel = typeModel;
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
                writeType(writer, typeModel);  //one model type per descriptor file
                writer.writeEndDocument();
                return null;
            } catch (XMLStreamException | IOException ex) {
                throw new IOException("Failed to complete writing acetate model "
                        + "descriptor file.", ex);
            }
        }

        private void writeType(XMLStreamWriter writer, ModelType typeModel)
                throws XMLStreamException, IOException {
            writer.writeStartElement(XML_ELEMENT_TYPE);
            writer.writeAttribute(XML_ATTRIBUTE_CLASSNAME, typeModel.getTypeName());
            writeNillableArray(writer, typeModel.getModelAnnotations(),
                    XML_ELEMENT_ANNOTATIONS, this::writeAnnotation);
            writeNillableArray(writer, typeModel.getModelFields(),
                    XML_ELEMENT_FIELDS, this::writeField);
            writeNillableArray(writer, typeModel.getModelMethods(),
                    XML_ELEMENT_METHODS, this::writeMethod);
            writer.writeEndElement();
        }

        private void writeMethod(XMLStreamWriter writer, ModelMethod m)
                throws XMLStreamException {
            writer.writeStartElement(XML_ELEMENT_METHOD);
            writer.writeAttribute(XML_ATTRIBUTE_NAME, m.getName());
            writeNillableArray(writer, m.getModelAnnotations(),
                    XML_ELEMENT_ANNOTATIONS, this::writeAnnotation);
            writeNillableArray(writer, m.getParameters(),
                    XML_ELEMENT_PARAMETERS, this::writeParameter);
            writeNillableArray(writer, m.getExceptions(),
                    XML_ELEMENT_EXCEPTIONS, this::writeException);
            if (m.getReturnType() != null) {
                writeTypeUse(writer, m.getReturnType(), XML_ELEMENT_RETURN, null);
            }
            writer.writeEndElement();
        }

        private void writeException(XMLStreamWriter writer,
                ModelTypeUse<? extends Throwable> ex) throws XMLStreamException {
            writeTypeUse(writer, ex, XML_ELEMENT_EXCEPTION, null);
        }

        private void writeParameter(XMLStreamWriter writer, ModelParameter p)
                throws XMLStreamException {
            writeTypeUse(writer, p, XML_ELEMENT_PARAMETER,
                    ModelParameter::getName);
        }

        private void writeField(XMLStreamWriter writer, ModelField f) throws XMLStreamException {
            writeTypeUse(writer, f, XML_ELEMENT_FIELD, ModelField::getName);
        }

        /**
         *
         *
         * @param <M>
         * @param writer
         * @param model
         * @param elementName
         * @param localNameFunction function to return type use name or null if
         * is anonymous
         * @throws XMLStreamException
         */
        private <M extends ModelTypeUse> void writeTypeUse(
                XMLStreamWriter writer, M model,
                String elementName,
                Function<M, String> localNameFunction)
                throws XMLStreamException {
            writer.writeStartElement(elementName);
            if (localNameFunction != null) {
                writer.writeAttribute(XML_ATTRIBUTE_NAME, localNameFunction.apply(model));
            }
            writer.writeAttribute(XML_ATTRIBUTE_CLASSNAME, model.getName());
            writeNillableArray(writer, model.getUseModelAnnotations(),
                    XML_ELEMENT_ANNOTATIONS, this::writeAnnotation);
            writer.writeEndElement();
        }

        private void writeAnnotation(XMLStreamWriter writer,
                ModelAnnotation a) throws XMLStreamException {
            writer.writeStartElement(XML_ELEMENT_MODEL_ANNOTATION);
            writer.writeCharacters(a.getAnnotationName());
            writer.writeEndElement();
        }

        private <M extends ModelElement> void writeNillableArray(
                XMLStreamWriter writer, M[] models,
                String wrapperElementName, XMLStreamWriterConsumer<M> itemOperation)
                throws XMLStreamException {
            if (models.length == 0) {
                return;
            }

            writer.writeStartElement(wrapperElementName);
            for (M m : models) {
                itemOperation.write(writer, m);
            }
            writer.writeEndElement();
        }

        @FunctionalInterface
        private interface XMLStreamWriterConsumer<M extends ModelElement> {

            void write(XMLStreamWriter writer, M model) throws XMLStreamException;
        }
    }

}
