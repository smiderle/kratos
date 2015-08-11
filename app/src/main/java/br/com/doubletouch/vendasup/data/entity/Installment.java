package br.com.doubletouch.vendasup.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LADAIR on 16/03/2015.
 */
public class Installment implements Serializable {

    public Installment() {
    }

    public Installment(Integer ID) {
        this.ID = ID;
    }

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

    private Integer idMobile;

    private boolean setSyncPending;

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

    public Integer getIdMobile() {
        return idMobile;
    }

    public void setIdMobile(Integer idMobile) {
        this.idMobile = idMobile;
    }

    public boolean isSetSyncPending() {
        return setSyncPending;
    }

    public void setSetSyncPending(boolean setSyncPending) {
        this.setSyncPending = setSyncPending;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Installment that = (Installment) o;

        if (ID != null ? !ID.equals(that.ID) : that.ID != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return ID != null ? ID.hashCode() : 0;
    }
}
