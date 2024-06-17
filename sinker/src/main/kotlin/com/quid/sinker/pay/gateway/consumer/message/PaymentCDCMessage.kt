package com.quid.sinker.pay.gateway.consumer.message

import com.fasterxml.jackson.annotation.JsonProperty
import com.quid.sinker.pay.domain.PaymentStatus
import java.math.BigDecimal

data class PaymentCDCMessage(
    val schema: Schema,
    val payload: Payload
) {

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
    val id: String,
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