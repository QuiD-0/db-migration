package com.quid.sinker.pay.gateway.consumer

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class KafkaPayConsumer {

    @KafkaListener(topics = ["mysql.old_db.PAYMENT"])
    fun consume(message: String, ack: Acknowledgment) {
        println("Consumed message: $message")
    }
}