package br.com.doubletouch.vendasup.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LADAIR on 06/04/2015.
 */
public class OrderItem {

    public OrderItem() {
    }

    @SerializedName("id")
    private Long ID;

    private Integer organizationID;

    private Integer branchID;

    private Integer sequence;

    private Order order;

    private Double salePrice;

    private Double discount;

    private String observation;

    private Product product;

    private Double quantity;

    private PriceTable priceTable;




    public void addQuantity(Double quantity){

        this.quantity += quantity;
    }

    public void removeQuantity(Double quantity){

        this.quantity -= quantity;
        if(this.quantity < 0 ) {
            this.quantity = 0.0;
        }
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public PriceTable getPriceTable() {
        return priceTable;
    }

    public void setPriceTable(PriceTable priceTable) {
        this.priceTable = priceTable;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
