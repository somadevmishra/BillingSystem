package com.manual.billing.dto.response

import java.math.BigDecimal

class ItemResponse(

    val id: Long,

    val itemCode: String?,

    val name: String,

    val description: String?,

    val category: CategoryResponse?,

    val unitPrice: BigDecimal,

    val gstPercentage: BigDecimal,

    val active: Boolean
)