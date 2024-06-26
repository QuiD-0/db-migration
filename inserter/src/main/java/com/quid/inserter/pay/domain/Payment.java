package com.quid.inserter.pay.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Payment(
    Long id,
    String transactionKey,
    String referenceKey,
    PaymentStatus status,
    BigDecimal amount,
    String currency,
    LocalDateTime regDate,
    LocalDateTime modDate
) {
    public Payment(String referenceKey, BigDecimal amount, String currency) {
        this(null, new TransactionKey().issue(), referenceKey, PaymentStatus.PENDING, amount,
            currency, LocalDateTime.now(), LocalDateTime.now());
    }

    public Payment done() {
        return new Payment(this.id, this.transactionKey, this.referenceKey, PaymentStatus.SUCCESS,
            this.amount, this.currency, this.regDate, LocalDateTime.now());
    }

    public String toResponse() {
        return """
            {
                "referenceKey": "%s",
                "transactionKey": "%s",
                "status": "%s",
                "payDate": "%s"
            }
            """
            .trim()
            .formatted(this.referenceKey, this.transactionKey, this.status.name(), LocalDateTime.now());
    }
}
