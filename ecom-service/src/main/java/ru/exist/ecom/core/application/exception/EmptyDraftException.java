package ru.exist.ecom.core.application.exception;

public class EmptyDraftException extends RuntimeException {
  public EmptyDraftException(String message) {
    super(message);
  }
}
