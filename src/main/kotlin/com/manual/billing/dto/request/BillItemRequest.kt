package com.manual.billing.dto.request

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class BillItemRequest(

    @field:NotNull
    val itemId: Long,

    @field:DecimalMin("0.001")
    val quantity: BigDecimal
)