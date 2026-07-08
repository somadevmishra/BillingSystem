package com.manual.billing.controller

import com.manual.billing.dto.request.BillItemRequest
import com.manual.billing.dto.request.CancelBillRequest
import com.manual.billing.dto.request.CreateBillRequest
import com.manual.billing.dto.request.PaymentRequest
import com.manual.billing.dto.response.BillResponse
import com.manual.billing.service.BillService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/bills")
class BillController(
    private val billService: BillService
) {

    @PostMapping
    fun createBill(
        @Valid @RequestBody request: CreateBillRequest
    ): BillResponse =
        billService.create(request)

    @GetMapping("/{id}")
    fun getBill(
        @PathVariable id: Long
    ): BillResponse =
        billService.findById(id)

    @GetMapping
    fun getAllBills(): List<BillResponse> =
        billService.findAll()

    @PostMapping("/{id}/items")
    fun addItem(
        @PathVariable id: Long,
        @Valid @RequestBody request: BillItemRequest
    ): BillResponse =
        billService.addItem(id, request)

    @PutMapping("/{id}/items/{itemId}")
    fun updateItem(
        @PathVariable id: Long,
        @PathVariable itemId: Long,
        @Valid @RequestBody request: BillItemRequest
    ): BillResponse =
        billService.updateItem(id, itemId, request)

    @DeleteMapping("/{id}/items/{itemId}")
    fun removeItem(
        @PathVariable id: Long,
        @PathVariable itemId: Long
    ): BillResponse =
        billService.removeItem(id, itemId)

    @PostMapping("/{id}/finalize")
    fun finalizeBill(
        @PathVariable id: Long,
        @Valid @RequestBody request: PaymentRequest
    ): BillResponse =
        billService.finalizeBill(id, request)

    @PostMapping("/{id}/cancel")
    fun cancelBill(
        @PathVariable id: Long,
        @Valid @RequestBody request: CancelBillRequest
    ): BillResponse =
        billService.cancel(id, request)
}