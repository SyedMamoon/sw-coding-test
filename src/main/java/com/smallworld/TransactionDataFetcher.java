package com.smallworld;

import com.smallworld.data.Transaction;
import com.smallworld.util.JsonParser;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionDataFetcher {

  /** Returns the sum of the amounts of all transactions */
  public double getTotalTransactionAmount() {
    return getAllTransactions().stream().distinct().toList().stream()
        .mapToDouble(Transaction::getAmount)
        .sum();
  }

  public List<Transaction> getAllTransactions() {
    return JsonParser.getAllTransaction();
  }

  /** Returns the sum of the amounts of all transactions sent by the specified client */
  public double getTotalTransactionAmountSentBy(String senderFullName) {
    return getAllTransactions().stream()
        .distinct()
        .filter(transaction -> transaction.getSenderFullName().equals(senderFullName))
        .mapToDouble(Transaction::getAmount)
        .sum();
  }

  /** Returns the highest transaction amount */
  public double getMaxTransactionAmount() {
    return getAllTransactions().stream()
        .distinct()
        .max(Comparator.comparingDouble(Transaction::getAmount))
        .orElseThrow(UnsupportedOperationException::new)
        .getAmount();
  }

  /** Counts the number of unique clients that sent or received a transaction */
  public long countUniqueClients() {
    return getAllTransactions().stream()
        .collect(Collectors.groupingBy(Transaction::getSenderFullName, Collectors.counting()))
        .size();
  }

  /**
   * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
   * issue that has not been solved
   */
  public boolean hasOpenComplianceIssues(String clientFullName) {
    return getAllTransactions().stream()
        .anyMatch(
            transaction ->
                (transaction.getBeneficiaryFullName().equals(clientFullName)
                        || transaction.getSenderFullName().equals(clientFullName))
                    && Boolean.FALSE.equals(transaction.getIssueSolved()));
  }

  /** Returns all transactions indexed by beneficiary name */
  public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() {
    return getAllTransactions().stream()
        .collect(Collectors.groupingBy(Transaction::getSenderFullName));
  }

  /** Returns the identifiers of all open compliance issues */
  public Set<Integer> getUnsolvedIssueIds() {
    return getAllTransactions().stream()
        .filter(transaction -> transaction.getIssueSolved().equals(Boolean.FALSE))
        .map(Transaction::getIssueId)
        .collect(Collectors.toSet());
  }

  /** Returns a list of all solved issue messages */
  public List<String> getAllSolvedIssueMessages() {

    return getAllTransactions().stream()
        .filter(transaction -> transaction.getIssueSolved().equals(Boolean.TRUE))
        .map(Transaction::getIssueMessage)
        .toList();
  }

  /** Returns the 3 transactions with the highest amount sorted by amount descending */
  public List<Transaction> getTop3TransactionsByAmount() {
    return getAllTransactions().stream()
        .sorted(Comparator.comparing(Transaction::getAmount).reversed())
        .limit(3)
        .toList();
  }

  /** Returns the senderFullName of the sender with the most total sent amount */
  public Optional<String> getTopSender() {

    Map<String, Double> transactionsSumBySenderName =
        getAllTransactions().stream()
            .collect(
                Collectors.groupingBy(
                    Transaction::getSenderFullName,
                    Collectors.summingDouble(Transaction::getAmount)));

    return Optional.ofNullable(
        Collections.max(transactionsSumBySenderName.entrySet(), Map.Entry.comparingByValue())
            .getKey());
  }
}
