package com.quid.sinker.pay.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import com.quid.sinker.pay.gateway.consumer.message.PaymentCDCMessage
import com.quid.sinker.pay.gateway.repository.PaymentCDCRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Thread.sleep

@Service
class PaymentProcessor(
    private val objectMapper: ObjectMapper,
    private val paymentCDCRepository: PaymentCDCRepository,
) {
    val log = LoggerFactory.getLogger(this::class.java)!!

    fun divideAndSave(message: String) {
        val cdcMessage = objectMapper.readValue(message, PaymentCDCMessage::class.java)
        log.info("Processing payment: ${cdcMessage.payment}")
        paymentCDCRepository.persist(cdcMessage.payment, cdcMessage.paymentResponse)
        sleep(2_000)
    }
}