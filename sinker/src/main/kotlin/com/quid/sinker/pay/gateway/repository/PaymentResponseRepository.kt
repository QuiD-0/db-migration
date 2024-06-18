package com.quid.sinker.pay.gateway.repository

import com.quid.sinker.pay.domain.PaymentResponse
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PaymentResponseRepository(
    private val jdbcClient: JdbcClient
) {

    @Transactional
    fun persist(paymentResponse: PaymentResponse?) {
        paymentResponse
            ?.let {
                val query = """
                    INSERT INTO payment_response (id, transaction_key, reference_key, status, pay_date)
                    VALUES (:id, :transaction_key, :reference_key, :status, :pay_date) 
                    ON CONFLICT (id) DO UPDATE SET 
                    reference_key = EXCLUDED.reference_key,
                    status = EXCLUDED.status,
                    pay_date = EXCLUDED.pay_date
                """.trimIndent()

            jdbcClient.sql(query)
                .params(
                    mapOf(
                        "id" to it.id,
                        "transaction_key" to it.transactionKey,
                        "reference_key" to it.referenceKey,
                        "status" to it.status.name,
                        "pay_date" to it.payDate
                    )
                )
                .query()
            }
    }

}