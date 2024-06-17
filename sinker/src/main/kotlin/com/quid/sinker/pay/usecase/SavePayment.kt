package com.quid.sinker.pay.usecase

import com.quid.sinker.pay.gateway.repository.PaymentRepository
import com.quid.sinker.pay.gateway.repository.PaymentResponseRepository
import org.springframework.stereotype.Service

@Service
class SavePayment(
    private val paymentRepository: PaymentRepository,
    private val paymentResponseRepository: PaymentResponseRepository
) {

    fun persist() {
        paymentRepository.save()
        paymentResponseRepository.save()
    }
}