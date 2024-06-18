package com.quid.sinker.pay.gateway.repository

import com.quid.sinker.pay.domain.Payment
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PaymentRepository(
    private val jdbcClient: JdbcClient
) {

    @Transactional
    fun persist(payment: Payment) {
        val query = """
            INSERT INTO payment (id, transaction_key, reference_key, status, amount, currency, reg_date, mod_date)
            VALUES (:id, :transaction_key, :reference_key, :status, :amount, :currency, :reg_date, :mod_date)
            ON CONFLICT (id) DO UPDATE SET 
            transaction_key = EXCLUDED.transaction_key,
            reference_key = EXCLUDED.reference_key,
            status = EXCLUDED.status, 
            amount = EXCLUDED.amount, 
            currency = EXCLUDED.currency,
            reg_date = EXCLUDED.reg_date,
            mod_date = EXCLUDED.mod_date
        """.trimIndent()

        jdbcClient.sql(query)
            .params(
                mapOf(
                    "id" to payment.id,
                    "transaction_key" to payment.transactionKey,
                    "reference_key" to payment.referenceKey,
                    "status" to payment.status.name,
                    "amount" to payment.amount,
                    "currency" to payment.currency,
                    "reg_date" to payment.regDate,
                    "mod_date" to payment.modDate
                )
            ).update()
    }
}