package com.example.devopsdemo.exceptions;

public class UsersServiceException extends RuntimeException {
  public UsersServiceException(String message) {
    super(message);
  }
}
