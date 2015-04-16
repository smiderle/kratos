package br.com.doubletouch.vendasup.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Entidade produto utilizado na camada data.
 * Created by LADAIR on 26/01/2015.
 */
public class Product implements Serializable{

    @SerializedName("id")
    private Integer ID;
    private Integer organizationID;
    private Integer branchID;
    private String description;
    private String reference;
    private String packaging;
    private Double salePrice;
    private String barcode;
    private Double stockAmount;
    private Boolean active;
    private Boolean excluded;
    private String pictureUrl;
    private boolean syncPending;

    /**
     * Código que é gravado quando um cliente é inserido pelo mobile. Para poder fazer um update, posteriormente, quando for inserido no banco de dados postgresql com um novo id.
     */
    private Integer idMobile;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(Double stockAmount) {
        this.stockAmount = stockAmount;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getExcluded() {
        return excluded;
    }

    public void setExcluded(Boolean excluded) {
        this.excluded = excluded;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Integer getIdMobile() {
        return idMobile;
    }

    public void setIdMobile(Integer idMobile) {
        this.idMobile = idMobile;
    }

    public boolean isSyncPending() {
        return syncPending;
    }

    public void setSyncPending(boolean syncPending) {
        this.syncPending = syncPending;
    }

    public String getProductIdAndDescription(){

        return this.getID()+" - "+ this.description;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!ID.equals(product.ID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
