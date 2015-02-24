package br.com.doubletouch.vendasup.data.exception;

import br.com.doubletouch.vendasup.exception.KratosException;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class CustomerNotFoundException extends KratosException {


    public CustomerNotFoundException() {
        super();
    }

    public CustomerNotFoundException(final String message) {
        super(message);
    }

    public CustomerNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CustomerNotFoundException(final Throwable cause) {
        super(cause);
    }
}
