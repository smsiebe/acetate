package org.geoint.acetate.bind.example.pojo;

import java.util.Objects;

/**
 * Simple object containing an enum which is used as an aggregate for POJO
 * binding testing.
 */
public class ProductSpecificationPojo {

    private String id;
    private String code;
    private String name;
    private DataTypePojo dataType;

    public ProductSpecificationPojo() {
    }

    public ProductSpecificationPojo(String id, String code, String name, DataTypePojo dataType) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dataType = dataType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataTypePojo getDataType() {
        return dataType;
    }

    public void setDataType(DataTypePojo dataType) {
        this.dataType = dataType;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final ProductSpecificationPojo other = (ProductSpecificationPojo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
