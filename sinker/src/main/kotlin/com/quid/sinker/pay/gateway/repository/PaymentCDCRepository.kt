package com.quid.sinker.pay.gateway.repository

import com.quid.sinker.pay.domain.Payment
import com.quid.sinker.pay.domain.PaymentResponse
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PaymentCDCRepository(
    private val paymentRepository: PaymentRepository,
    private val paymentResponseRepository: PaymentResponseRepository,
) {
    val log = LoggerFactory.getLogger(this::class.java)!!

    @Transactional
    fun persist(payment: Payment, paymentResponse: PaymentResponse?) {
        log.info("Saving payment: $payment")
        paymentRepository.persist(payment)
        paymentResponseRepository.persist(paymentResponse)
    }
}