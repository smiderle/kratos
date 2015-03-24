package br.com.doubletouch.vendasup.data.entity;

import java.io.Serializable;

/**
 * Created by LADAIR on 20/03/2015.
 */
public class Organization implements Serializable{

    public Organization() {
    }

    public Organization(Integer organizationID) {
        this.organizationID = organizationID;
    }

    private Integer organizationID;

    private String name;

    public Integer getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(Integer organizationID) {
        this.organizationID = organizationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
