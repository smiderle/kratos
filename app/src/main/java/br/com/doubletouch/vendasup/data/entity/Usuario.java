package br.com.doubletouch.vendasup.data.entity;

/**
 * Created by LADAIR on 29/01/2015.
 */
public class Usuario {

    //TODO Provisório até ser implementado
    private Integer organizationID = 116;
    private String email = "junior@gmail.com";

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
}
