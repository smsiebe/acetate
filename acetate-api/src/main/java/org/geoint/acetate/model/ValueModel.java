package org.geoint.acetate.model;

import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.model.annotation.Domain;
import org.geoint.acetate.model.constraint.Constrained;

/**
 * Data-bearing data model component.
 *
 * A Value differs from an Object in that a ValueModel does not contain other
 * data model components - it simply models a data value itself.
 *
 * @param <T> java class of the data value
 */
@Domain(name = "acetate", version = 1)
public interface ValueModel<T> extends DataModel<T>, Constrained {

    /**
     * Default character codec to use for this domain object.
     *
     * @return default character codec
     */
    CharacterCodec<T> getCharacterCodec();

    /**
     * Default binary codec to use for this domain object.
     *
     * @return default binary codec
     */
    BinaryCodec<T> getBinaryCodec();
}
