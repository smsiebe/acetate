package org.geoint.acetate.model.view;

import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.model.DomainObject;

/**
 * A contextual variant of a domain defined {@link DomainObject}.
 *
 * @param <T>
 */
public interface DomainObjectView<T> extends DomainObject<T> {

    /**
     * The "base" object model, without any view-specific aspects applied.
     *
     * @return "base" domain model object
     */
    DomainObject<T> getBaseModel();

    /**
     * Returns the codec to use to convert this object to a binary stream for
     * this view.
     *
     * @return view-specific codec
     */
    @Override
    public BinaryCodec<T> getBinaryCodec();

    /**
     * Returns the codec to use to convert the domain object to a character
     * stream for this view.
     *
     * @return view-specific codec
     */
    @Override
    public CharacterCodec<T> getCharacterCodec();

}
