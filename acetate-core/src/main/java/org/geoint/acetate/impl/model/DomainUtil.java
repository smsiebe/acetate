package org.geoint.acetate.impl.model;

import org.geoint.acetate.model.ComponentAddress;

/**
 *
 */
public class DomainUtil {

    public static String uniqueDomainId(String name, long version) {
        return String.join("-", name, String.valueOf(version));
    }

    public static String uniqueDomainId(ComponentAddress address) {
        return uniqueDomainId(address.getDomainName(), address.getDomainVersion());
    }
}
