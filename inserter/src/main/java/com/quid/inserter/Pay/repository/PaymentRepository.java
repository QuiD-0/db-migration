package com.quid.inserter.Pay.repository;

import com.quid.inserter.Pay.domain.Payment;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
public class PaymentRepository {

    private final JdbcClient jdbcClient;

    public PaymentRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional
    public void save(Payment payment) {
        log.info("== SAVE PAYMENT ==");
        String query = "INSERT INTO Payment (transactionKey, referenceKey, status, amount, currency, regDate, modDate) VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcClient.sql(query)
            .params(List.of(payment.transactionKey(), payment.referenceKey(), payment.status().name(), payment.amount(), payment.currency(), payment.regDate(), payment.modDate()))
            .update();
    }

    @Transactional(readOnly = true)
    public Optional<Payment> findByTransactionKey(String transactionKey) {
        log.info("== FIND PAYMENT BY TRANSACTION ID {} ==", transactionKey);
        return jdbcClient.sql(
                "SELECT * FROM Payment p WHERE transactionKey = ?")
            .param(transactionKey)
            .query(Payment.class)
            .optional();
    }

    @Transactional
    public void update(Payment done) {
        log.info("== UPDATE PAYMENT ==");
        String query = "UPDATE Payment SET status = ?, modDate = ? WHERE id = ?";

        jdbcClient.sql(query)
            .params(List.of(done.status().name(), done.modDate(), done.id()))
            .update();
    }
}
