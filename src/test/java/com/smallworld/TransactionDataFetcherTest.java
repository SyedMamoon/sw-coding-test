package com.smallworld;

import com.smallworld.data.Transaction;
import com.smallworld.util.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class TransactionDataFetcherTest {

  private TransactionDataFetcher transactionDataFetcher;

  @BeforeEach
  void setup() {
    transactionDataFetcher = new TransactionDataFetcher();
  }

  @Test
  void testGetTotalTransactionAmount() {
    Assertions.assertEquals(2232.0, transactionDataFetcher.getTotalTransactionAmount());
  }

  @Test
  void testGetTotalTransactionAmountSentByWhenSenderExists() {
    double totalTransactionAmountSentBy =
        transactionDataFetcher.getTotalTransactionAmountSentBy("Mamoon");
    Assertions.assertEquals(1502.0, totalTransactionAmountSentBy);
  }

  @Test
  void getTotalTransactionAmountSentByWhenSenderNotExists() {
    double totalTransactionAmountSentBy =
        transactionDataFetcher.getTotalTransactionAmountSentBy("John Doe");
    Assertions.assertEquals(0.0, totalTransactionAmountSentBy);
  }

  @Test
  void testGetMaxTransactionAmount() {
    Assertions.assertEquals(1502.0, transactionDataFetcher.getMaxTransactionAmount());
  }

  @Test
  void testCountUniqueClients() {
    Assertions.assertEquals(2, transactionDataFetcher.countUniqueClients());
  }

  @Test
  void testHasOpenComplianceIssues() {
    Assertions.assertTrue(transactionDataFetcher.hasOpenComplianceIssues("Tom Shelby"));
  }

  @Test
  void testHasOpenComplianceIssuesWhenAllIssuesResolved() {
    Assertions.assertFalse(transactionDataFetcher.hasOpenComplianceIssues("Mamoon"));
  }

  @Test
  void testGetTransactionsByBeneficiaryName() {
    List<Transaction> transactions = getTransactions();

    Map<String, List<Transaction>> actualResult =
        transactionDataFetcher.getTransactionsByBeneficiaryName();

    Map<String, List<Transaction>> expectedResult = new HashMap<>();

    expectedResult.put(
        "Tom Shelby",
        new ArrayList<>(List.of(transactions.get(0), transactions.get(1), transactions.get(2))));
    expectedResult.put("Mamoon", new ArrayList<>(List.of(transactions.get(3))));

    for (Map.Entry<String, List<Transaction>> entry : actualResult.entrySet()) {
      ReflectionAssert.assertReflectionEquals(entry.getValue(), expectedResult.get(entry.getKey()));
    }
  }

  @Test
  void testGetUnsolvedIssueIds() {
    Set<Integer> actualResult = transactionDataFetcher.getUnsolvedIssueIds();
    Set<Integer> expectedResult = new HashSet<>(List.of(1, 3));
    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  void testGetAllSolvedIssueMessages() {
    List<String> actualResult = transactionDataFetcher.getAllSolvedIssueMessages();
    List<String> expectedResult = List.of("Try Again", "Never gonna give you up");
    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  void testGetTop3TransactionsByAmount() {
    List<Transaction> transactions = getTransactions();
    List<Transaction> actualResult = transactionDataFetcher.getTop3TransactionsByAmount();
    List<Transaction> expectedResult =
        new ArrayList<>(List.of(transactions.get(3), transactions.get(0), transactions.get(1)));
    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  void getTopSender() {
    Assertions.assertEquals(Optional.of("Mamoon"), transactionDataFetcher.getTopSender());
  }

  /**
   * Get Transaction Data Using JSON Parser
   *
   * @return List<Transction>
   */
  private List<Transaction> getTransactions() {
    return JsonParser.getAllTransaction();
  }
}
