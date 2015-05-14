package org.geoint.acetate.model;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Context-unique path for a domain model component.
 */
public interface ComponentPath {

    String getDomainName();

    long getDomainVersion();

    String asString();

    default URI toURI() throws URISyntaxException {
        try {
            return new URI(asString());
        } catch (URISyntaxException ex) {
            assert false : "context path could not be composed as a URI "
                    + "in the default ContextPath#toURI method.";
            throw ex;
        }
    }

}
