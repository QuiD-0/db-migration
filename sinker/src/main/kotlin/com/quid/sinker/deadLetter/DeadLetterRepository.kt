package com.quid.sinker.deadLetter

import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class DeadLetterRepository(
    private val jdbcClient: JdbcClient
) {
    private val log = LoggerFactory.getLogger(this::class.java)


    @Transactional
    fun save(message: String) {
        log.info("Saving dead letter: $message")
        val sql = "INSERT INTO DEAD_LETTER (message) VALUES (:message)"

        jdbcClient.sql(sql)
            .param("message", message)
            .update()
    }
}