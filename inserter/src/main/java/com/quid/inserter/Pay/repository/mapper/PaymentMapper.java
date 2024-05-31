package com.quid.inserter.Pay.repository.mapper;

import com.quid.inserter.Pay.domain.Payment;
import com.quid.inserter.Pay.domain.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentMapper(Long id,
                            String transactionKey,
                            String referenceKey,
                            String status,
                            BigDecimal amount,
                            String currency,
                            LocalDateTime regDate,
                            LocalDateTime modDate) {
    public Payment toPayment() {
        return new Payment(this.id, this.transactionKey, this.referenceKey, PaymentStatus.valueOf(this.status), this.amount, this.currency, this.regDate, this.modDate);
    }
}
