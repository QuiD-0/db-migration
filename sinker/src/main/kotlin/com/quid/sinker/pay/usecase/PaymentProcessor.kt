package com.quid.sinker.pay.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import com.quid.sinker.pay.gateway.consumer.message.PaymentCDCMessage
import com.quid.sinker.pay.gateway.repository.PaymentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PaymentProcessor(
    private val objectMapper: ObjectMapper,
    private val paymentRepository: PaymentRepository,
) {
    val log = LoggerFactory.getLogger(this::class.java)!!

    fun divideAndSave(message: String) {
        val cdcMessage = objectMapper.readValue(message, PaymentCDCMessage::class.java)
        log.info("Processing payment: ${cdcMessage.payment}")
        paymentRepository.persist(cdcMessage.payment, cdcMessage.paymentResponse)
    }
}