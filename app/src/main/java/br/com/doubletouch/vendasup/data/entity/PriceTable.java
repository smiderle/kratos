package br.com.doubletouch.vendasup.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class PriceTable implements Serializable {

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

    public static final class PriceTableDB {

        private PriceTableDB() {
        }

        public static final String TABELA = "TABELA_PRECO";
        public static final String ID = "_id";
        public static final String IDEMPRESA = "IDEMPRESA";
        public static final String IDFILIAL = "IDFILIAL";
        public static final String IDTABELAPRECO = "IDTABELAPRECO";
        public static final String DESCRICAO = "DESCRICAO";
        public static final String PERCENTAGE = "PERCENTAGE";
        public static final String ACRESCIMO = "ACRESCIMO";
        public static final String ATIVO = "ATIVO";
        public static final String EXCLUIDO = "EXCLUIDO";

        public static final String[] COLUNAS = new String[]{ID, IDEMPRESA, IDFILIAL, IDTABELAPRECO, DESCRICAO, PERCENTAGE, ACRESCIMO, ATIVO, EXCLUIDO};
    }
}
