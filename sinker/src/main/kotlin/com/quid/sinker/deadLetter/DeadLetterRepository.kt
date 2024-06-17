package com.quid.sinker.deadLetter

import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Component

@Component
class DeadLetterRepository(
    private val jdbcClient: JdbcClient
) {
    fun save(message: String) {
        val sql = "INSERT INTO DEAD_LETTER (message) VALUES (?)"

        jdbcClient.sql(sql)
            .param(message)
            .query()
    }
}