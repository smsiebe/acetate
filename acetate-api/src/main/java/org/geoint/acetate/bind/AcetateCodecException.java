package org.geoint.acetate.bind;

import org.geoint.acetate.AcetateException;

/**
 *
 */
public class AcetateCodecException extends AcetateException {

    private final Class<? extends DataCodec> codec;
    private final Class<? extends DataBinder> binder;
    private final String path;

    public AcetateCodecException(Class<? extends DataCodec> codec,
            Class<? extends DataBinder> binder, String path) {
        this.codec = codec;
        this.binder = binder;
        this.path = path;
    }

    public AcetateCodecException(Class<? extends DataCodec> codec,
            Class<? extends DataBinder> binder, String path, String message) {
        super(message);
        this.codec = codec;
        this.binder = binder;
        this.path = path;
    }

    public AcetateCodecException(Class<? extends DataCodec> codec,
            Class<? extends DataBinder> binder, String path, String message, Throwable cause) {
        super(message, cause);
        this.codec = codec;
        this.binder = binder;
        this.path = path;
    }

    public AcetateCodecException(Class<? extends DataCodec> codec,
            Class<? extends DataBinder> binder, String path, Throwable cause) {
        super(cause);
        this.codec = codec;
        this.binder = binder;
        this.path = path;
    }

    public Class<? extends DataCodec> getCodec() {
        return codec;
    }

    public Class<? extends DataBinder> getBinder() {
        return binder;
    }

    public String getPath() {
        return path;
    }

}
