package com.quid.sinker.pay.gateway.consumer.message

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class PaymentCDCMessageTest {

    @Test
    fun mapper() {
        val string = """
            {    
                "referenceKey": "f472b2ddd1b048ce947c56604b7edecb",
                "transactionKey": "07539613a24b495ab300f8b4a521e2a0",
                "status": "SUCCESS",
                "payDate": "2024-06-17T22:28:01.236673"
            }
        """.trimIndent()

        val readValue = ObjectMapper()
            .readValue(string, PaymentResponseMessage::class.java)

        assertDoesNotThrow { readValue.toDomain() }
    }
}