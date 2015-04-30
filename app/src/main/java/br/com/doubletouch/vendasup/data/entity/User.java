package br.com.doubletouch.vendasup.data.entity;

import java.util.List;

/**
 * Created by LADAIR on 20/03/2015.
 */
public class User {

    public User() {
    }

    public User(Integer userID) {
        this.userID = userID;
    }

    private Integer userID;

    private Integer organizationID;

    private String email;

    private String password;

    private String name;

    private boolean active;

    private boolean portalAccess;

    private List<UserBranchOffice> userBranches;

    private String phoneNumber;

    private String cellPhone;

    private String pictureUrl;

    private String linkFacebook;

    private String linkGooglePlus;

    private String skype;

    private String street;

    private String district;

    private String number;

    private String postalCode;

    private String cpfCnpj;

    private String inscricao;

    private String contactName;

    private String observation;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(Integer organizationID) {
        this.organizationID = organizationID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPortalAccess() {
        return portalAccess;
    }

    public void setPortalAccess(boolean portalAccess) {
        this.portalAccess = portalAccess;
    }

    public List<UserBranchOffice> getUserBranches() {
        return userBranches;
    }

    public void setUserBranches(List<UserBranchOffice> userBranches) {
        this.userBranches = userBranches;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getLinkFacebook() {
        return linkFacebook;
    }

    public void setLinkFacebook(String linkFacebook) {
        this.linkFacebook = linkFacebook;
    }

    public String getLinkGooglePlus() {
        return linkGooglePlus;
    }

    public void setLinkGooglePlus(String linkGooglePlus) {
        this.linkGooglePlus = linkGooglePlus;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
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

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
