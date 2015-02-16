package br.com.doubletouch.vendasup.exception;

/**
 * Created by LADAIR on 27/01/2015.
 */
public class KratosException extends Exception {

    public KratosException() {
    }

    public KratosException(String detailMessage) {
        super(detailMessage);
    }

    public KratosException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public KratosException(Throwable throwable) {
        super(throwable);
    }
}
