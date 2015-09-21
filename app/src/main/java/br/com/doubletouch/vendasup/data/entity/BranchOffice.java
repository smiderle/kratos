package br.com.doubletouch.vendasup.data.entity;

import java.io.Serializable;

import br.com.doubletouch.vendasup.data.entity.Organization;

/**
 * Created by LADAIR on 20/03/2015.
 */
public class BranchOffice implements Serializable{

    public BranchOffice() {
    }

    public BranchOffice(Integer branchOfficeID) {
        this.branchOfficeID = branchOfficeID;
    }

    private Integer branchOfficeID;

    private Organization organization;


    private String realName;

    private String fancyName;

    private String phoneNumber;

    private String faxNumber;

    private String street;

    private String district;

    private String number;

    private String postalCode;

    private String email;

    private String website;

    private String manager;

    private String cnpj;

    private String addressComplement;

    private Double maximumDiscount;

    private boolean emailNotification;

    private boolean showStockProduct;

    private boolean negativeStockProduct;

    private boolean sellerRegisterCustomer;

    private boolean sendEmailCustomer;

    private String actionOverdue;

    private String actionCreditLimit;

    public Integer getBranchOfficeID() {
        return branchOfficeID;
    }

    public void setBranchOfficeID(Integer branchOfficeID) {
        this.branchOfficeID = branchOfficeID;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFancyName() {
        return fancyName;
    }

    public void setFancyName(String fancyName) {
        this.fancyName = fancyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getAddressComplement() {
        return addressComplement;
    }

    public void setAddressComplement(String addressComplement) {
        this.addressComplement = addressComplement;
    }

    public Double getMaximumDiscount() {
        return maximumDiscount;
    }

    public void setMaximumDiscount(Double maximumDiscount) {
        this.maximumDiscount = maximumDiscount;
    }

    public boolean isEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public boolean isShowStockProduct() {
        return showStockProduct;
    }

    public void setShowStockProduct(boolean showStockProduct) {
        this.showStockProduct = showStockProduct;
    }

    public boolean isNegativeStockProduct() {
        return negativeStockProduct;
    }

    public void setNegativeStockProduct(boolean negativeStockProduct) {
        this.negativeStockProduct = negativeStockProduct;
    }

    public boolean isSellerRegisterCustomer() {
        return sellerRegisterCustomer;
    }

    public void setSellerRegisterCustomer(boolean sellerRegisterCustomer) {
        this.sellerRegisterCustomer = sellerRegisterCustomer;
    }

    public boolean isSendEmailCustomer() {
        return sendEmailCustomer;
    }

    public void setSendEmailCustomer(boolean sendEmailCustomer) {
        this.sendEmailCustomer = sendEmailCustomer;
    }

    public String getActionOverdue() {
        return actionOverdue;
    }

    public void setActionOverdue(String actionOverdue) {
        this.actionOverdue = actionOverdue;
    }

    public String getActionCreditLimit() {
        return actionCreditLimit;
    }

    public void setActionCreditLimit(String actionCreditLimit) {
        this.actionCreditLimit = actionCreditLimit;
    }

    @Override
    public String toString() {
        return getBranchOfficeID() +" - " + getFancyName();
    }
}
