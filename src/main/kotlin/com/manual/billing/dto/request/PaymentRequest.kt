package com.manual.billing.dto.request

import com.manual.billing.enums.PaymentMode
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class PaymentRequest(

    @field:NotNull
    val paymentMode: PaymentMode,

    @field:DecimalMin("0.00")
    val amountPaid: BigDecimal
)