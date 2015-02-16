package br.com.doubletouch.vendasup.data.net;

/**
 * Created by LADAIR on 27/01/2015.
 */

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = -5584906004219908759L;
    public static String STATUS_SUCCESS = "SUCCESS";
    public static String STATUS_FAILURE = "FAILURE";
    public static String CODE_SUCESS = "200" ;


    private String status = STATUS_SUCCESS;
    private String code = CODE_SUCESS;
    private String message;
    private Long hour;

    private T payload;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public Long getHour() {
        return hour;
    }

    public void setHour(Long hour) {
        this.hour = hour;
    }
}