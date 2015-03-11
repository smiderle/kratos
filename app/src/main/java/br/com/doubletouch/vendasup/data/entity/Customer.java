package br.com.doubletouch.vendasup.data.entity;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LADAIR on 17/02/2015.
 */
public class Customer implements Serializable {

    @SerializedName("id")
    private Integer ID;
    private Integer organizationID;
    private Integer branchID;
    private String customerID;
    private String name;
    private String nickName;
    private Integer personType;
    private String cpfCnpj;
    private String incricao;
    private String commercialPhone;
    private String homePhone;
    private String cellPhone;
    private String postalCode;
    private String addressComplement;
    private String observation;
    private String faxNumber;
    private String street;
    private String district;
    private String number;
    private String email;
    private Double defaultDiscount;
    private Double creditLimit;
    private Integer defaultSeller;
    private boolean active;
    private boolean excluded ;
    private String pictureUrl;
    private Integer priceTable;
    //private Integer installment;
    private Integer formPayment;
    private boolean syncPending;

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

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getIncricao() {
        return incricao;
    }

    public void setIncricao(String incricao) {
        this.incricao = incricao;
    }

    public String getCommercialPhone() {
        return commercialPhone;
    }

    public void setCommercialPhone(String commercialPhone) {
        this.commercialPhone = commercialPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddressComplement() {
        return addressComplement;
    }

    public void setAddressComplement(String addressComplement) {
        this.addressComplement = addressComplement;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getDefaultDiscount() {
        return defaultDiscount;
    }

    public void setDefaultDiscount(Double defaultDiscount) {
        this.defaultDiscount = defaultDiscount;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Integer getDefaultSeller() {
        return defaultSeller;
    }

    public void setDefaultSeller(Integer defaultSeller) {
        this.defaultSeller = defaultSeller;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Integer getPriceTable() {
        return priceTable;
    }

    public void setPriceTable(Integer priceTable) {
        this.priceTable = priceTable;
    }
/*
    public Integer getInstallment() {
        return installment;
    }

    public void setInstallment(Integer installment) {
        this.installment = installment;
    }*/

    public Integer getFormPayment() {
        return formPayment;
    }

    public void setFormPayment(Integer formPayment) {
        this.formPayment = formPayment;
    }

    public boolean isSyncPending() {
        return syncPending;
    }

    public void setSyncPending(boolean syncPending) {
        this.syncPending = syncPending;
    }

    public String getCustomerIdAndName(){
        if(this.customerID != null && this.name != null){
            return customerID+" - "+ name;
        }

        if(this.customerID == null && this.name != null){
            return name;
        }

        return "";
    }

    public static final class CustomerDB {

        private CustomerDB(){}

        public static final String TABELA = "CLIENTE";
        public static final String ID = "_id";
        public static final String IDCLIENTE = "IDCLIENTE";
        public static final String IDEMPRESA = "IDEMPRESA";
        public static final String IDFILIAL = "IDFILIAL";
        public static final String NOME = "NOME";
        public static final String APELIDO = "APELIDO";
        public static final String TIPO_PESSOA = "TIPO_PESSOA";
        public static final String CPF_CNPJ = "CPF_CNPJ";
        public static final String INSCRICAO = "INSCRICAO";
        public static final String FONE_COMERCIAL = "FONE_COMERCIAL";
        public static final String FONE_RESIDENCIAL = "FONE_RESIDENCIAL";
        public static final String FONE_CELULAR = "FONE_CELULAR";
        public static final String CEP = "CEP";
        public static final String COMPLEMENTO = "COMPLEMENTO";
        public static final String OBSERVACAO = "OBSERVACAO";
        public static final String FAX = "FAX";
        public static final String RUA = "RUA";
        public static final String BAIRRO = "BAIRRO";
        public static final String NUMERO = "NUMERO";
        public static final String EMAIL = "EMAIL";

        public static final String DESCONTO_PADRAO = "DESCONTO_PADRAO";
        public static final String ATIVO = "ATIVO";
        public static final String EXCLUIDO = "EXCLUIDO";
        public static final String TABELA_PRECO = "TABELA_PRECO";
        public static final String PARCELAMENTO = "PARCELAMENTO";
        public static final String FORMA_PAGAMENTO = "FORMA_PAGAMENTO";
        public static final String URL_IMAGEM = "URL_IMAGEM";
        public static final String SYNC_PENDENTE= "SYNC_PENDENTE";

        public static final String[] COLUNAS = new String[]{ID, IDEMPRESA,IDFILIAL,IDCLIENTE, NOME, APELIDO, TIPO_PESSOA, CPF_CNPJ, INSCRICAO, FONE_COMERCIAL,FONE_RESIDENCIAL,FONE_CELULAR, CEP, COMPLEMENTO,
                OBSERVACAO,FAX,RUA,BAIRRO,NUMERO,EMAIL,DESCONTO_PADRAO,ATIVO, EXCLUIDO, TABELA_PRECO, PARCELAMENTO, FORMA_PAGAMENTO, URL_IMAGEM, SYNC_PENDENTE};
    }
}
