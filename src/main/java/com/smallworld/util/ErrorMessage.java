package com.smallworld.util;

public class ErrorMessage {
  public static final String CLASS_INSTANTIATION_NOT_ALLOWED =
      "Instantiation of Class is not allowed.";

  private ErrorMessage() {
    throw new UnsupportedOperationException(CLASS_INSTANTIATION_NOT_ALLOWED);
  }
}
