package com.quid.sinker.pay.domain

import java.math.BigDecimal
import java.time.LocalDateTime

data class Payment(
    val id: Long? = null,
    val transactionKey: String,
    val referenceKey: String,
    val status: PaymentStatus,
    val amount: BigDecimal,
    val currency: String,
    val regDate: LocalDateTime,
    val modDate: LocalDateTime
) {
}