package br.com.doubletouch.vendasup.data.entity;

import java.io.Serializable;

/**
 *
 * Created by LADAIR on 29/01/2015.
 */
public class Usuario implements Serializable {

    //TODO Provisório até ser implementado
    private Integer organizationID = 116;
    private String email = "junior@gmail.com";
    private Integer branchID = 1;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(Integer organizationID) {
        this.organizationID = organizationID;
    }

    public Integer getBranchID() {
        return branchID;
    }

    public void setBranchID(Integer branchID) {
        this.branchID = branchID;
    }
}
