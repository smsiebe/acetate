package org.geoint.acetate.bind.example.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * POJO data object that aggregate objects in a collection.
 */
public class ProductTypePojo {

    private String id;
    private String name;
    private Collection<ProductSpecificationPojo> specifications;

    public ProductTypePojo() {
    }

    public ProductTypePojo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<ProductSpecificationPojo> getSpecifications() {
        if (specifications == null) {
            specifications = new ArrayList<>();
        }
        return specifications;
    }

    public void setSpecifications(Collection<ProductSpecificationPojo> specifications) {
        this.specifications = specifications;
    }

    public void addSpecification(ProductSpecificationPojo spec) {
        this.getSpecifications().add(spec);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final ProductTypePojo other = (ProductTypePojo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
