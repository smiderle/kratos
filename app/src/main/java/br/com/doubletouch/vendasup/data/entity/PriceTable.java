package br.com.doubletouch.vendasup.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class PriceTable implements Serializable {

    public PriceTable() {
    }

    public PriceTable(Integer ID) {
        this.ID = ID;
    }

    @SerializedName("id")
    private Integer ID;

    private Integer organizationID;

    private Integer branchID;

    private Integer priceTableID;

    private String description;

    private Double percentage;

    private boolean increase;

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

    public Integer getPriceTableID() {
        return priceTableID;
    }

    public void setPriceTableID(Integer priceTableID) {
        this.priceTableID = priceTableID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public boolean isIncrease() {
        return increase;
    }

    public void setIncrease(boolean increase) {
        this.increase = increase;
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
        return getID() + " - " + getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceTable that = (PriceTable) o;

        if (ID != null ? !ID.equals(that.ID) : that.ID != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return ID != null ? ID.hashCode() : 0;
    }
}
