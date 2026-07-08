package com.manual.billing.dto.response

import java.math.BigDecimal

data class BillItemResponse(

    val itemId: Long,

    val itemCode: String,

    val itemName: String,

    val quantity: BigDecimal,

    val unitPrice: BigDecimal,

    val gstPercentage: BigDecimal,

    val lineSubtotal: BigDecimal,

    val taxAmount: BigDecimal,

    val lineTotal: BigDecimal
)