package com.quid.sinker.pay.gateway.consumer

import com.quid.sinker.deadLetter.DeadLetterRepository
import com.quid.sinker.pay.usecase.PayTransformer
import org.springframework.kafka.annotation.DltHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.retrytopic.DltStrategy.FAIL_ON_ERROR
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class KafkaPayConsumer(
    private val transformer: PayTransformer,
    private val deadLetterRepository: DeadLetterRepository
) {

    @RetryableTopic(
        attempts = "3",
        dltStrategy = FAIL_ON_ERROR
    )
    @KafkaListener(topics = ["mysql.old_db.PAYMENT"])
    fun consume(message: String, ack: Acknowledgment) {
        transformer.divideResponse(message)
        ack.acknowledge()
    }

    @DltHandler
    fun deadLetter(message: String, ack: Acknowledgment) {
        deadLetterRepository.save(message)
        ack.acknowledge()
    }
}