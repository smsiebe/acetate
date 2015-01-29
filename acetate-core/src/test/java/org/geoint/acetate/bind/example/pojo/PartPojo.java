package org.geoint.acetate.bind.example.pojo;

import java.util.Objects;

/**
 * POJO for an individual part.
 */
public class PartPojo {

    private String id;
    private ProductPojo product;
    private double cost;
    private String address;

    public PartPojo() {
    }

    public PartPojo(String id, ProductPojo product, double cost, String address) {
        this.id = id;
        this.product = product;
        this.cost = cost;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductPojo getProduct() {
        return product;
    }

    public void setProduct(ProductPojo product) {
        this.product = product;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final PartPojo other = (PartPojo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
