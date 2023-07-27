package com.smallworld.data;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Transaction {
    // Represent your transaction data here.
    private Integer mtn;
    private Double amount;
    private String senderFullName;
    private Integer senderAge;
    private String beneficiaryFullName;
    private Integer beneficiaryAge;
    private Integer issueId;
    private Boolean issueSolved;
    private String issueMessage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return mtn.equals(that.mtn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mtn);
    }
}
