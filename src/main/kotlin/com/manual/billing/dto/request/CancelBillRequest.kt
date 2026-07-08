package com.manual.billing.dto.request

import jakarta.validation.constraints.NotBlank

data class CancelBillRequest(

    @field:NotBlank
    val reason: String
)