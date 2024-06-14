package com.quid.sinker.pay.gateway.consumer

import com.quid.sinker.deadLetter.DeadLetterQueue
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
    private val deadLetterQueue: DeadLetterQueue
) {

    @RetryableTopic(
        attempts = "3",
        dltStrategy = FAIL_ON_ERROR
    )
    @KafkaListener(topics = ["mysql.old_db.PAYMENT"])
    fun consume(message: String, ack: Acknowledgment) {
        transformer.divideResponse(message)
        throw RuntimeException("Error")
        ack.acknowledge()
    }

    @DltHandler
    fun deadLetter(message: String, ack: Acknowledgment) {
        deadLetterQueue.save(message)
        ack.acknowledge()
    }
}