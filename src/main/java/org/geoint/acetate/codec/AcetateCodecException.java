package org.geoint.acetate.codec;

import org.geoint.acetate.AcetateException;

/**
 * Thrown when there is an irrecoverable problem with the codec/transformation
 * process.
 */
public class AcetateCodecException extends AcetateException {

    private final Class<? extends AcetateCodec> codecType;

    public AcetateCodecException(Class<? extends AcetateCodec> codecType) {
        this.codecType = codecType;
    }

    public AcetateCodecException(Class<? extends AcetateCodec> codecType, String message) {
        super(message);
        this.codecType = codecType;
    }

    public AcetateCodecException(Class<? extends AcetateCodec> codecType, String message, Throwable cause) {
        super(message, cause);
        this.codecType = codecType;
    }

    public AcetateCodecException(Class<? extends AcetateCodec> codecType, Throwable cause) {
        super(cause);
        this.codecType = codecType;
    }

    public Class<? extends AcetateCodec> getCodecType() {
        return codecType;
    }

}
