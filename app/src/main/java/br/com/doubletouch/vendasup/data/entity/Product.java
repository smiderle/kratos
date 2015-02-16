package br.com.doubletouch.vendasup.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Entidade produto utilizado na camada data.
 * Created by LADAIR on 26/01/2015.
 */
public class Product {

    @SerializedName("id")
    private Integer ID;
    private String productID;
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

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public static final class ProductDB {

        private ProductDB(){}

        public static final String TABELA = "PRODUTO";
        public static final String ID = "ID";
        public static final String IDPRODUTO = "IDPRODUTO";
        public static final String IDEMPRESA = "IDEMPRESA";
        public static final String IDFILIAL = "IDFILIAL";
        public static final String DESCRICAO = "DESCRICAO";
        public static final String REFERENCIA = "REFERENCIA";
        public static final String EMBALAGEM = "EMBALAGEM";
        public static final String PRECO_VENDA = "PRECO_VENDA";
        public static final String CODBAR = "CODBAR";
        public static final String ESTOQUE = "ESTOQUE";
        public static final String ATIVO = "ATIVO";
        public static final String EXCLUIDO = "EXCLUIDO";
        public static final String URL_IMAGEM = "URL_IMAGEM";

        public static final String[] COLUNAS = new String[]{ID, IDEMPRESA,IDFILIAL,IDPRODUTO, DESCRICAO, REFERENCIA, EMBALAGEM, PRECO_VENDA, CODBAR, ESTOQUE,ATIVO, EXCLUIDO, URL_IMAGEM };
    }
}
