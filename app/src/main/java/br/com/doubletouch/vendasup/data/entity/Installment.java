package br.com.doubletouch.vendasup.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LADAIR on 16/03/2015.
 */
public class Installment implements Serializable {


    @SerializedName("id")
    private Integer ID;

    private Integer organizationID;

    private Integer branchID;

    private Integer installmentID;

    private String description;

    private String installmentsDays;

    private Double tax;

    private boolean active;

    private boolean excluded;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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

    public Integer getInstallmentID() {
        return installmentID;
    }

    public void setInstallmentID(Integer installmentID) {
        this.installmentID = installmentID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstallmentsDays() {
        return installmentsDays;
    }

    public void setInstallmentsDays(String installmentsDays) {
        this.installmentsDays = installmentsDays;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(boolean excluded) {
        this.excluded = excluded;
    }


    @Override
    public String toString() {
        return getID() +" - " + getDescription();
    }
}
