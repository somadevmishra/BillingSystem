package com.manual.billing.dto.response

import java.math.BigDecimal
import java.time.LocalDateTime

data class BillResponse(

    val id: Long,

    val billNo: String,

    val billDate: LocalDateTime,

    val status: String,

    val customerName: String?,

    val customerMobile: String?,

    val remarks: String?,

    val subtotal: BigDecimal,

    val discountAmount: BigDecimal,

    val taxAmount: BigDecimal,

    val grandTotal: BigDecimal,

    val paymentMode: String?,

    val paymentStatus: String?,

    val amountPaid: BigDecimal,

    val balanceAmount: BigDecimal,

    val items: List<BillItemResponse>
)