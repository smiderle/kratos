package br.com.doubletouch.vendasup.data.exception;

import br.com.doubletouch.vendasup.exception.KratosException;

/**
 * Created by LADAIR on 12/02/2015.
 */
public class ProductNotFoundException extends KratosException {


    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(final String message) {
        super(message);
    }

    public ProductNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundException(final Throwable cause) {
        super(cause);
    }
}
