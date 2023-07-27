package com.smallworld.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonParser {

  public static List<Transaction> getAllTransaction() {
    ObjectMapper mapper = new ObjectMapper();

    try {
      File initialFile = new File(FilePathConstants.TRANSACTION_JSON_FILE);
      return mapper.readValue(initialFile, new TypeReference<List<Transaction>>() {});
    } catch (IOException e) {
      throw new UnsupportedOperationException("Error occurred while reading transactions :" + e);
    }
  }
}
