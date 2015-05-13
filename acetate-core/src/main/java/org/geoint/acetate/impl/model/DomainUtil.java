
package org.geoint.acetate.impl.model;

/**
 *
 */
public class DomainUtil {

    public static String uniqueDomainId (String name, long version) {
          return String.join("-", name, String.valueOf(version));
    }
}
