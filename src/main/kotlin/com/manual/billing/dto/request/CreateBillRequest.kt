package com.manual.billing.dto.request

import jakarta.validation.Valid
import java.util.Collections.emptyList

data class CreateBillRequest(

    val customerName: String? = null,

    val customerMobile: String? = null,

    val remarks: String? = null
)

