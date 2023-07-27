package com.smallworld.util;

import static com.smallworld.util.ErrorMessage.CLASS_INSTANTIATION_NOT_ALLOWED;

public class FilePathConstants {
  public static final String TRANSACTION_JSON_FILE = "src/main/resources/transactions.json";

  private FilePathConstants() {
    throw new UnsupportedOperationException(CLASS_INSTANTIATION_NOT_ALLOWED);
  }
}
