package org.geoint.acetate.data;

import java.nio.charset.Charset;
import org.geoint.acetate.domain.annotation.Accessor;
import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.domain.model.DataModel;

/**
 * Context for a {@link DataCodec codec} operation.
 * 
 * @see DataCodec
 */
@Model(name = "codecContext", displayName = "Codec Context",
        domain = DataModel.ACETATE_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public final class CodecContext {

    private final String contentType;
    private final Charset charset;

    public CodecContext(String contentType, Charset charset) {
        this.contentType = contentType;
        this.charset = charset;
    }

    @Accessor
    public String getContentType() {
        return contentType;
    }

    @Accessor
    public Charset getCharset() {
        return charset;
    }

    @Override
    public String toString() {
        return String.format("Content-type: %s$; encoding: %s$",
                contentType, charset.name());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.contentType != null ? this.contentType.hashCode() : 0);
        hash = 59 * hash + (this.charset != null ? this.charset.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CodecContext other = (CodecContext) obj;
        if ((this.contentType == null) ? (other.contentType != null) : !this.contentType.equals(other.contentType)) {
            return false;
        }
        if (this.charset != other.charset && (this.charset == null || !this.charset.equals(other.charset))) {
            return false;
        }
        return true;
    }

}
