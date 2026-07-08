package com.manual.billing.service.impl

import com.manual.billing.dto.request.*
import com.manual.billing.dto.response.BillResponse
import com.manual.billing.engine.BillCalculationEngine
import com.manual.billing.entity.BillEntity
import com.manual.billing.enums.BillStatus
import com.manual.billing.enums.PaymentStatus
import com.manual.billing.mapper.toResponse
import com.manual.billing.repository.BillRepository
import com.manual.billing.repository.ItemRepository
import com.manual.billing.service.BillService
import com.manual.billing.service.SequenceService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
@Transactional
class BillServiceImpl(
    private val billRepository: BillRepository,
    private val itemRepository: ItemRepository,
    private val sequenceService: SequenceService,
    private val engine: BillCalculationEngine
) : BillService {
    override fun create(request: CreateBillRequest): BillResponse {

        val bill = BillEntity(
            billNo = sequenceService.nextBillNumber(),
            billDate = LocalDateTime.now(),

            status = BillStatus.OPEN,

            subtotal = BigDecimal.ZERO,
            discountAmount = BigDecimal.ZERO,
            taxAmount = BigDecimal.ZERO,
            grandTotal = BigDecimal.ZERO,

            customerName = request.customerName,
            customerMobile = request.customerMobile,
            remarks = request.remarks
        )

        return billRepository.save(bill).toResponse()
    }

    override fun update(id: Long, request: UpdateBillRequest): BillResponse {
        TODO("Not yet implemented")
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): BillResponse = getBill(id).toResponse()

    @Transactional(readOnly = true)
    override fun findAll(): List<BillResponse> = billRepository.findAllByOrderByBillDateDesc().map { it.toResponse() }


    override fun addItem(
        billId: Long, request: BillItemRequest
    ): BillResponse {

        val bill = getBill(billId)
        validateBillEditable(bill)

        val item = itemRepository.findById(request.itemId).orElseThrow { RuntimeException("Item not found") }

        val billItem = engine.createBillItem(
            bill = bill, item = item, quantity = request.quantity
        )

        bill.items.add(billItem)

        engine.recalculateBill(bill)

        return billRepository.save(bill).toResponse()
    }

    override fun updateItem(
        billId: Long, billItemId: Long, request: BillItemRequest
    ): BillResponse {

        val bill = getBill(billId)
        validateBillEditable(bill)

        val billItem = bill.items.firstOrNull {
            it.id == billItemId
        } ?: throw RuntimeException("Bill item not found")

        billItem.quantity = request.quantity

        engine.calculateLineItem(billItem)
        engine.recalculateBill(bill)

        return billRepository.save(bill).toResponse()
    }

    override fun removeItem(
        billId: Long, billItemId: Long
    ): BillResponse {

        val bill = getBill(billId)
        validateBillEditable(bill)

        val item = bill.items.firstOrNull {
            it.id == billItemId
        } ?: throw RuntimeException("Bill item not found")

        bill.items.remove(item)

        engine.recalculateBill(bill)

        return billRepository.save(bill).toResponse()
    }

    override fun finalizeBill(
        billId: Long, request: PaymentRequest
    ): BillResponse {

        val bill = getBill(billId)
        validateBillEditable(bill)

        // Recalculate before finalizing (safety)
        engine.recalculateBill(bill)

        bill.amountPaid = request.amountPaid
        bill.paymentMode = request.paymentMode

        bill.balanceAmount = bill.grandTotal.subtract(request.amountPaid)

        bill.paymentStatus = when {
            request.amountPaid >= bill.grandTotal -> PaymentStatus.PAID

            request.amountPaid > BigDecimal.ZERO -> PaymentStatus.PARTIAL

            else -> PaymentStatus.PENDING
        }

        bill.status = BillStatus.FINALIZED
        bill.finalizedAt = LocalDateTime.now()

        return billRepository.save(bill).toResponse()
    }

    override fun cancel(
        billId: Long, request: CancelBillRequest
    ): BillResponse {

        val bill = getBill(billId)

        if (bill.status == BillStatus.CANCELLED) {
            throw IllegalStateException("Bill ${bill.billNo} is already cancelled")
        }

        if (bill.status == BillStatus.FINALIZED) {
            throw IllegalStateException("Finalized bill cannot be cancelled")
        }

        bill.status = BillStatus.CANCELLED
        bill.cancelReason = request.reason
        bill.cancelledAt = LocalDateTime.now()

        return billRepository.save(bill).toResponse()
    }


    private fun getBill(id: Long): BillEntity = billRepository.findById(id).orElseThrow {
            EntityNotFoundException("Bill not found with id : $id")
        }

    private fun validateBillEditable(bill: BillEntity) {

        when (bill.status) {

            BillStatus.CANCELLED -> {
                throw IllegalStateException(
                    "Bill ${bill.billNo} is cancelled and cannot be modified"
                )
            }

            BillStatus.FINALIZED -> {
                throw IllegalStateException(
                    "Bill ${bill.billNo} is finalized and cannot be modified"
                )
            }

            BillStatus.OPEN -> {
                // allowed state → do nothing
            }
        }
    }
}