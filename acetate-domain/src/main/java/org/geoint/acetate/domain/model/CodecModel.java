package org.geoint.acetate.domain.model;

import java.util.Set;
import org.geoint.acetate.data.DataCodec;
import org.geoint.acetate.domain.annotation.Accessor;
import org.geoint.acetate.domain.annotation.Entity;
import org.geoint.acetate.domain.annotation.MultiComposite;
import org.geoint.acetate.domain.annotation.Query;

/**
 *
 * @param <T>
 */
@Entity(name = "codecModel", displayName = "Codec Model",
        domain = DataModel.ACETATE_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public interface CodecModel<T> {

    /**
     * The content types to use this codec.
     *
     * @return supported data content type
     */
    @Accessor
    @MultiComposite(name = "contentTypes", itemName = "contentType")
    Set<String> getSupportedContentTypes();

    /**
     * The data model this codec can be applied.
     *
     * @return supported data model type
     */
    @Accessor
    DataModel<T> getDataModel();

    @Query
    DataCodec<T> getCodec();
}
