package net.luisduarte.revolut.exercise.endpoints.model

import java.math.BigDecimal

data class AccountDTO(val id: String?, val balance: BigDecimal, val owner: String) {
    constructor(): this(null, BigDecimal.ZERO, "")
}