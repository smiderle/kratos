package br.com.doubletouch.vendasup.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LADAIR on 23/03/2015.
 */
public class Goal {

    @SerializedName("id")
    private Integer ID;

    private Integer organizationID;

    private Integer branchID;

    private Integer userID;

    private Integer month;

    private Integer year;

    private Integer yearMonth;

    private Double value;

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

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
