package br.com.doubletouch.vendasup.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LADAIR on 01/04/2015.
 */
public class Order implements Serializable{

    public Order() {
    }

    public Order(Long ID) {
        this.ID = ID;
    }

    @SerializedName("id")
    private Long ID;

    private Integer organizationID;

    private Integer branchID;

    private Customer customer;

	private User user;

    private Integer userID;

    private Installment installment;

    private Double grossValue;

    private Double netValue;

    private Double totalDiscount;

    private String observation;

    private Date issuanceTime;

    private List<OrderItem> ordersItens = new ArrayList<>();

    private List<OrderPayment> ordersPayments = new ArrayList<>();

    private Integer formPayment;

    private boolean excluded;


    public OrderItem containsProduct(Product product){

        if(ordersItens != null){
            for (OrderItem orderItem : ordersItens){
                if(orderItem.getProduct().equals(product)){
                    return  orderItem;
                }
            }
        }

        return null;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Installment getInstallment() {
        return installment;
    }

    public void setInstallment(Installment installment) {
        this.installment = installment;
    }

    public Double getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(Double grossValue) {
        this.grossValue = grossValue;
    }

    public Double getNetValue() {
        return netValue;
    }

    public void setNetValue(Double netValue) {
        this.netValue = netValue;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getIssuanceTime() {
        return issuanceTime;
    }

    public void setIssuanceTime(Date issuanceTime) {
        this.issuanceTime = issuanceTime;
    }

    public Integer getFormPayment() {
        return formPayment;
    }

    public void setFormPayment(Integer formPayment) {
        this.formPayment = formPayment;
    }

    public boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(boolean excluded) {
        this.excluded = excluded;
    }

    public List<OrderItem> getOrdersItens() {
        return ordersItens;
    }

    public void setOrdersItens(List<OrderItem> ordersItens) {
        this.ordersItens = ordersItens;
    }

    public List<OrderPayment> getOrdersPayments() {
        return ordersPayments;
    }

    public void setOrdersPayments(List<OrderPayment> ordersPayments) {
        this.ordersPayments = ordersPayments;
    }

}
