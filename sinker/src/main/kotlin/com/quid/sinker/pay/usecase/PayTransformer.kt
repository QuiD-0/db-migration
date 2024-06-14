package com.quid.sinker.pay.usecase

import org.springframework.stereotype.Service

@Service
class PayTransformer {

    fun divideResponse(message: String) {
        println("Divide response: $message")
    }
}