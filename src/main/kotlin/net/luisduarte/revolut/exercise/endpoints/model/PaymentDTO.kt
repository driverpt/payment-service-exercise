package net.luisduarte.revolut.exercise.endpoints.model

import java.math.BigDecimal

data class PaymentDTO(val id: String?, val amount: BigDecimal, val from: String, val to: String) {
    constructor(): this(null, BigDecimal.ZERO, "", "")
}