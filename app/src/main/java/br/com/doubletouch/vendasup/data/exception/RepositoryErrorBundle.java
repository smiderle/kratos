/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package br.com.doubletouch.vendasup.data.exception;

import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;

/**
 * Wrapper around Exceptions used to manage errors in the repository.
 */
public class RepositoryErrorBundle implements ErrorBundle {

  private final Exception exception;

  public RepositoryErrorBundle(Exception exception) {
    this.exception = exception;
  }

  public Exception getException() {
    return exception;
  }

  public String getErrorMessage() {
    String message = "";
    if (this.exception != null) {
        message = this.exception.getMessage();
    }
    return message;
  }
}
