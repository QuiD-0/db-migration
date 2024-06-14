package com.quid.sinker.pay.domain

data class PaymentResponse(
    val id: Long? = null,
    val referenceKey: String,
    val transactionKey: String,
    val status: PaymentStatus,
    val payDate: String
) {
}