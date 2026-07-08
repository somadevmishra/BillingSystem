package com.manual.billing.service

import com.manual.billing.dto.request.*
import com.manual.billing.dto.response.BillResponse


interface BillService {

    fun create(request: CreateBillRequest): BillResponse

    fun update(id: Long, request: UpdateBillRequest): BillResponse

    fun findById(id: Long): BillResponse

    fun findAll(): List<BillResponse>

    fun addItem(
        billId: Long,
        request: BillItemRequest
    ): BillResponse

    fun updateItem(
        billId: Long,
        billItemId: Long,
        request: BillItemRequest
    ): BillResponse

    fun removeItem(
        billId: Long,
        billItemId: Long
    ): BillResponse

    fun finalizeBill(
        billId: Long,
        request: PaymentRequest
    ): BillResponse

    fun cancel(
        billId: Long,
        request: CancelBillRequest
    ): BillResponse
}