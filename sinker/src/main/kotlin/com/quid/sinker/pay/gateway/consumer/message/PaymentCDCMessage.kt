package com.quid.sinker.pay.gateway.consumer.message

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.quid.sinker.pay.domain.Payment
import com.quid.sinker.pay.domain.PaymentResponse
import com.quid.sinker.pay.domain.PaymentStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class PaymentCDCMessage(
    val schema: Schema,
    val payload: Payload
) {

    val payment: Payment
        get() = Payment(
            id = payload.id,
            transactionKey = payload.transactionKey,
            referenceKey = payload.referenceKey,
            status = payload.status,
            amount = payload.amount,
            currency = payload.currency,
            regDate = LocalDateTime.ofEpochSecond(payload.regDate, 0, ZoneOffset.UTC),
            modDate = LocalDateTime.ofEpochSecond(payload.modDate, 0, ZoneOffset.UTC)
        )

    val paymentResponse: PaymentResponse?
        get() = payload.responseJson?.let {
            ObjectMapper()
                .readValue(it, PaymentResponseMessage::class.java)
                .toDomain()
        }
}

data class Schema(
    val fields: List<Field>,
    val type: String,
    val optional: Boolean,
    val name: String,
) {

}

data class Field(
    val field: String,
    val optional: Boolean,
    val type: String,
    val name: String?,
    val version: Int?
) {

}

data class Payload(
    @JsonProperty("ID")
    val id: Long,
    @JsonProperty("TRANSACTION_KEY")
    val transactionKey: String,
    @JsonProperty("REFERENCE_KEY")
    val referenceKey: String,
    @JsonProperty("STATUS")
    val status: PaymentStatus,
    @JsonProperty("AMOUNT")
    val amount: BigDecimal,
    @JsonProperty("CURRENCY")
    val currency: String,
    @JsonProperty("REG_DATE")
    val regDate: Long,
    @JsonProperty("MOD_DATE")
    val modDate: Long,
    @JsonProperty("RESPONSE_JSON")
    val responseJson: String?
) {

}

data class PaymentResponseMessage(
    @JsonProperty("referenceKey")
    val referenceKey: String,
    @JsonProperty("transactionKey")
    val transactionKey: String,
    @JsonProperty("status")
    val status: PaymentStatus,
    @JsonProperty("payDate")
    val payDate: String
) {
    fun toDomain(): PaymentResponse =
        PaymentResponse(
            transactionKey = transactionKey,
            referenceKey = referenceKey,
            status = status,
            payDate = LocalDateTime.parse(payDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))
        )
}