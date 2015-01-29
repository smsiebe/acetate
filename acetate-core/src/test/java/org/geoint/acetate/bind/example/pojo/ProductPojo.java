package org.geoint.acetate.bind.example.pojo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * POJO for binding tests containing one-to-one object references as well as a
 * Map.
 */
public class ProductPojo {

    private String id;
    private ProductTypePojo type;
    private ManufacturerPojo manufacturer;
    private String name;
    private String model;
    private Map<String, String> specifications;

    public ProductPojo() {
    }

    public ProductPojo(String id, ProductTypePojo type, ManufacturerPojo manufacturer, String name, String model) {
        this.id = id;
        this.type = type;
        this.manufacturer = manufacturer;
        this.name = name;
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductTypePojo getType() {
        return type;
    }

    public void setType(ProductTypePojo type) {
        this.type = type;
    }

    public ManufacturerPojo getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerPojo manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Map<String, String> getSpecifications() {
        if (specifications == null) {
            specifications = new HashMap<>();
        }
        return specifications;
    }

    public void setSpecifications(Map<String, String> specifications) {
        this.specifications = specifications;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final ProductPojo other = (ProductPojo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
