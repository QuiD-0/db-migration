package com.quid.sinker.pay.gateway.repository

import com.quid.sinker.pay.domain.Payment
import com.quid.sinker.pay.domain.PaymentResponse
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PaymentRepository(
    private val jdbcClient: JdbcClient
) {
    val log = LoggerFactory.getLogger(this::class.java)!!

    @Transactional
    fun persist(payment: Payment, paymentResponse: PaymentResponse?) {
        log.info("Saving payment: $payment")
        savePayment(payment)
        paymentResponse
            ?.let { savePaymentResponse(it) }
    }

    private fun savePaymentResponse(it: PaymentResponse) {
        val query = """
            INSERT INTO payment_response (id, transaction_key, reference_key, status, pay_date)
            VALUES (?, ?, ?, ?, ?)  
            ON DUPLICATE KEY UPDATE
            transaction_key = VALUES(transaction_key),
            reference_key = VALUES(reference_key),
            status = VALUES(status),
            pay_date = VALUES(pay_date)
        """.trimIndent()

        jdbcClient.sql(query)
            .params(
                it.id,
                it.transactionKey,
                it.referenceKey,
                it.status.name,
                it.payDate
            ).query()
    }

    private fun savePayment(payment: Payment) {
        val query = """
            INSERT INTO payment (id, transaction_key, reference_key, status, amount, currency, reg_date, mod_date)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
            transaction_key = VALUES(transaction_key),
            reference_key = VALUES(reference_key),
            status = VALUES(status),
            amount = VALUES(amount),
            currency = VALUES(currency),
            reg_date = VALUES(reg_date),
            mod_date = VALUES(mod_date)
        """.trimIndent()

        jdbcClient.sql(query)
            .params(
                payment.id,
                payment.transactionKey,
                payment.referenceKey,
                payment.status.name,
                payment.amount,
                payment.currency,
                payment.regDate,
                payment.modDate
            ).query()
    }
}