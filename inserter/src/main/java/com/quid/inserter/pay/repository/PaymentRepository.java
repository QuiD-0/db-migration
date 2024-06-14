package com.quid.inserter.pay.repository;

import com.quid.inserter.pay.domain.Payment;
import com.quid.inserter.pay.repository.mapper.PaymentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        String query = "INSERT INTO Payment (TRANSACTION_KEY, REFERENCE_KEY, STATUS, AMOUNT, CURRENCY, REG_DATE, MOD_DATE) VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcClient.sql(query)
            .params(List.of(payment.transactionKey(), payment.referenceKey(), payment.status().name(), payment.amount(), payment.currency(), payment.regDate(), payment.modDate()))
            .update();
    }

    @Transactional(readOnly = true)
    public Optional<Payment> findByTransactionKey(String transactionKey) {
        log.info("== FIND PAYMENT BY TRANSACTION KEY {} ==", transactionKey);
        return jdbcClient.sql("SELECT * FROM PAYMENT WHERE TRANSACTION_KEY = ?")
            .param(transactionKey)
            .query(PaymentMapper.class)
            .optional()
            .map(PaymentMapper::toPayment);
    }

    @Transactional
    public void done(Payment done) {
        log.info("== UPDATE PAYMENT ==");
        String query = "UPDATE PAYMENT SET STATUS = ?, MOD_DATE = ?, RESPONSE_JSON = ? WHERE ID = ?";

        jdbcClient.sql(query)
            .params(List.of(done.status().name(), done.modDate(), done.toResponse(), done.id()))
            .update();
    }
}
