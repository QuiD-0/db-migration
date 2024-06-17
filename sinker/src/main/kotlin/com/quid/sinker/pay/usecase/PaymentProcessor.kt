package com.quid.sinker.pay.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import com.quid.sinker.pay.gateway.consumer.message.PaymentCDCMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Thread.sleep

@Service
class PaymentProcessor(
    private val objectMapper: ObjectMapper,
    private val savePayment: SavePayment,
) {
    val log = LoggerFactory.getLogger(this::class.java)!!

    fun divideAndSave(message: String) {
        val payment = objectMapper.readValue(message, PaymentCDCMessage::class.java)
        log.info("PaymentProcessor.divideAndSave() payment: $payment")
        sleep(10000)
        savePayment.persist()
    }
}