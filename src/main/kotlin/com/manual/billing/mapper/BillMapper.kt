package com.manual.billing.mapper

import com.manual.billing.dto.response.BillItemResponse
import com.manual.billing.dto.response.BillResponse
import com.manual.billing.entity.BillEntity
import com.manual.billing.entity.BillItemEntity

fun BillItemEntity.toResponse(): BillItemResponse {
    return BillItemResponse(
        itemId = this.item.id!!,
        itemCode = this.itemCode,
        itemName = this.itemName,
        quantity = this.quantity,
        unitPrice = this.unitPrice,
        gstPercentage = this.gstPercentage,
        lineSubtotal = this.lineSubtotal,
        taxAmount = this.taxAmount,
        lineTotal = this.lineTotal
    )
}

fun BillEntity.toResponse(): BillResponse {
    return BillResponse(
        id = this.id!!,
        billNo = this.billNo,
        billDate = this.billDate,
        status = this.status.name,
        customerName = this.customerName,
        customerMobile = this.customerMobile,
        remarks = this.remarks,
        subtotal = this.subtotal,
        discountAmount = this.discountAmount,
        taxAmount = this.taxAmount,
        grandTotal = this.grandTotal,
        paymentMode = this.paymentMode?.name,
        paymentStatus = this.paymentStatus?.name,
        amountPaid = this.amountPaid,
        balanceAmount = this.balanceAmount,
        items = this.items.map { it.toResponse() }
    )
}