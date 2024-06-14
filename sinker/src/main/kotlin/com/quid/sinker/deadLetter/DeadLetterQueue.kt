package com.quid.sinker.deadLetter

import org.springframework.stereotype.Component

@Component
class DeadLetterQueue {
    fun save(message: String) {
        println("Dead letter: $message")
    }
}