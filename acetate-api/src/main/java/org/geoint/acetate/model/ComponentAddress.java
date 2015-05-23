package org.geoint.acetate.model;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * A domain model component address uniquely identifies a model component. 
 *
 */
public interface ComponentAddress {

    /**
     * Domain name of the component.
     *
     * @return Domain name of the component.
     */
    String getDomainName();

    /**
     * Domain version of the component.
     *
     * @return Domain version of the component.
     */
    long getDomainVersion();

    /**
     * Contextually-unique component name.
     *
     * @return Contextually-unique component name.
     */
    String getComponentName();

    /**
     * Context path as a URI-formatted String.
     *
     *
     * @return Context path as a URI-formatted String.
     */
    String asString();

    /**
     * Context path as URI.
     *
     * @return model component URI
     * @throws URISyntaxException
     */
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
