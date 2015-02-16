package br.com.doubletouch.vendasup.data.net;

/**
 * Created by LADAIR on 27/01/2015.
 */

public class ServiceResponse<DOMAIN> {
    private DOMAIN value;
    private Long rowCount;

    public ServiceResponse() {
    }
    public ServiceResponse(final DOMAIN value, final Long rowCount) {
        this.value = value;
        this.rowCount = rowCount;
    }
    public Long getRowCount() {
        return rowCount;
    }
    public DOMAIN getValue() {
        return value;
    }
    public void setRowCount(final Long rowCount) {
        this.rowCount = rowCount;
    }
    public void setValue(final DOMAIN value) {
        this.value = value;
    }

}

