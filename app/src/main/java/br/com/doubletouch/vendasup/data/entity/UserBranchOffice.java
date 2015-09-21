package br.com.doubletouch.vendasup.data.entity;

import java.io.Serializable;

/**
 * Created by LADAIR on 20/03/2015.
 */
public class UserBranchOffice implements Serializable {

    private BranchOffice branchOffice;

    private Integer userID;

    private boolean enable;

    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
