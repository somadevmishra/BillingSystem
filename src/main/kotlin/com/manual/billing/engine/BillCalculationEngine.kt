package com.manual.billing.engine

import com.manual.billing.entity.BillEntity
import com.manual.billing.entity.BillItemEntity
import com.manual.billing.entity.ItemEntity
import org.springframework.stereotype.Component
import java.math.BigDecimal

/**
 * =========================================================
 * BillCalculationEngine
 * =========================================================
 *
 * RESPONSIBILITY:
 * This class contains ALL billing calculation logic for the system.
 *
 * It is NOT responsible for:
 * - database operations
 * - validation
 * - service orchestration
 *
 * It IS responsible for:
 * - Bill item creation
 * - Line item calculations (GST, subtotal, total)
 * - Bill-level recalculations
 *
 * ---------------------------------------------------------
 * WHY THIS CLASS EXISTS:
 *
 * In a POS/Billing system, calculations are:
 * - reusable
 * - deterministic
 * - business-critical
 *
 * Keeping them outside service layer ensures:
 * - clean separation of concerns
 * - easier testing
 * - reuse across modules (POS, Invoice, Reports)
 * ---------------------------------------------------------
 *
 * RULES:
 * - Never access repositories here
 * - Never perform validations here
 * - Only pure business calculations
 * =========================================================
 */

@Component
class BillCalculationEngine {

    fun createBillItem(
        bill: BillEntity,
        item: ItemEntity,
        quantity: BigDecimal
    ): BillItemEntity {

        val billItem = BillItemEntity(
            bill = bill,

            item = item,

            itemCode = item.itemCode,
            itemName = item.name,

            quantity = quantity,
            unitPrice = item.unitPrice,
            gstPercentage = item.gstPercentage,

            discountAmount = BigDecimal.ZERO,
            lineSubtotal = BigDecimal.ZERO,
            taxAmount = BigDecimal.ZERO,
            lineTotal = BigDecimal.ZERO
        )

        calculateLineItem(billItem)

        return billItem
    }

    fun calculateLineItem(item: BillItemEntity) {

        val subtotal = item.unitPrice.multiply(item.quantity)

        val tax = subtotal
            .multiply(item.gstPercentage)
            .divide(BigDecimal(100))

        val total = subtotal
            .add(tax)
            .subtract(item.discountAmount)

        item.lineSubtotal = subtotal
        item.taxAmount = tax
        item.lineTotal = total
    }

    fun recalculateBill(bill: BillEntity) {

        val subtotal = bill.items.fold(BigDecimal.ZERO) { acc, i ->
            acc + i.lineSubtotal
        }

        val tax = bill.items.fold(BigDecimal.ZERO) { acc, i ->
            acc + i.taxAmount
        }

        val itemDiscount = bill.items.fold(BigDecimal.ZERO) { acc, i ->
            acc + i.discountAmount
        }

        bill.subtotal = subtotal
        bill.taxAmount = tax

        bill.grandTotal =
            subtotal
                .add(tax)
                .subtract(itemDiscount)
                .subtract(bill.discountAmount)

        bill.balanceAmount = bill.grandTotal.subtract(bill.amountPaid)
    }
}