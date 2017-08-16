package com.restservice;

/**
 * Exception class to handle all the application related exceptions
 */
public class ApplicationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

 
  public ApplicationException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Takes a messages
   *
   * @param message the message
   */
  public ApplicationException(String message) {
    super(message);
  }
}
