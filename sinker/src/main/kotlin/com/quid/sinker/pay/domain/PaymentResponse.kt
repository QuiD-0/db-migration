package com.quid.sinker.pay.domain

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class PaymentResponse(
    val id: Long? = null,
    val referenceKey: String,
    val transactionKey: String,
    val status: PaymentStatus,
    val payDate: LocalDateTime
) {
}