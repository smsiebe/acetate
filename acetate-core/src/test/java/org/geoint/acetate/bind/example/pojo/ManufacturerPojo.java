package org.geoint.acetate.bind.example.pojo;

import java.net.URL;
import java.util.Objects;

/**
 * Simple POJO data object.
 */
public class ManufacturerPojo {

    private String id;
    private String name;
    private URL companyUrl;
    private URL ebayUrl;

    public ManufacturerPojo() {
    }

    public ManufacturerPojo(String id, String name, URL companyUrl, URL ebayUrl) {
        this.id = id;
        this.name = name;
        this.companyUrl = companyUrl;
        this.ebayUrl = ebayUrl;
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

    public URL getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(URL companyUrl) {
        this.companyUrl = companyUrl;
    }

    public URL getEbayUrl() {
        return ebayUrl;
    }

    public void setEbayUrl(URL ebayUrl) {
        this.ebayUrl = ebayUrl;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final ManufacturerPojo other = (ManufacturerPojo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
