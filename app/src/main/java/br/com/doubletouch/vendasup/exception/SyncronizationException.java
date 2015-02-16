package br.com.doubletouch.vendasup.exception;

/**
 * Created by LADAIR on 27/01/2015.
 */
public class SyncronizationException extends KratosException {

    public SyncronizationException() {
    }

    public SyncronizationException(String detailMessage) {
        super(detailMessage);
    }

    public SyncronizationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public SyncronizationException(Throwable throwable) {
        super(throwable);
    }
}
