package org.geoint.acetate.codec;

/**
 * Thrown when there is a data transformation problem using an acetate codec.
 */
public class AcetateTransformException extends AcetateCodecException {

    private final Class<?> dataItemType;
    private final Class<?> dataTransformType;

    public AcetateTransformException(Class<? extends AcetateCodec> codecType,
            Class<?> dataItemType,
            Class<?> dataTransformType) {
        super(codecType);
        this.dataItemType = dataItemType;
        this.dataTransformType = dataTransformType;
    }

    public AcetateTransformException(Class<? extends AcetateCodec> codecType,
            Class<?> dataItemType, Class<?> dataTransformType, String message) {
        super(codecType, message);
        this.dataItemType = dataItemType;
        this.dataTransformType = dataTransformType;
    }

    public AcetateTransformException(Class<? extends AcetateCodec> codecType,
            Class<?> dataItemType, Class<?> dataTransformType, String message,
            Throwable cause) {
        super(codecType, message, cause);
        this.dataItemType = dataItemType;
        this.dataTransformType = dataTransformType;
    }

    public AcetateTransformException(Class<? extends AcetateCodec> codecType,
            Class<?> dataItemType, Class<?> dataTransformType,
            Throwable cause) {
        super(codecType, cause);
        this.dataItemType = dataItemType;
        this.dataTransformType = dataTransformType;
    }

    public Class<?> getDataItemType() {
        return dataItemType;
    }

    public Class<?> getDataTransformType() {
        return dataTransformType;
    }

}
