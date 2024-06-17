package com.quid.sinker.pay.gateway.repository

import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository

@Repository
class PaymentResponseRepository(
    private val jdbcClient: JdbcClient
) {
    fun save() {
        println("PaymentResponseRepository.save()")
    }
}