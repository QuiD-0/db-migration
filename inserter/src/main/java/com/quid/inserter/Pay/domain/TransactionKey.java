package com.quid.inserter.Pay.domain;

import java.util.UUID;

public record TransactionKey(
    String transactionKey
) {
    public TransactionKey() {
        this(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    public String issue() {
        return this.transactionKey;
    }
}
