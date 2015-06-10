
package org.geoint.acetate.model.view;

import org.geoint.acetate.model.address.ComponentAddress;

/**
 * Address defining a view of a domain model.
 * 
 */
public interface ViewAddress extends ComponentAddress {

    ViewContext getContext();
    
}
