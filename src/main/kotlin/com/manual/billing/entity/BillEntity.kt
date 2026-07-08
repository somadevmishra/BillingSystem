package com.manual.billing.entity

import com.manual.billing.enums.BillStatus
import com.manual.billing.enums.PaymentMode
import com.manual.billing.enums.PaymentStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "bills")
class BillEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "bill_no", nullable = false, unique = true, length = 30)
    var billNo: String,

    @Column(name = "bill_date", nullable = false)
    var billDate: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    var status: BillStatus,

    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    var subtotal: BigDecimal,

    @Column(name = "discount_amount", nullable = false, precision = 12, scale = 2)
    var discountAmount: BigDecimal,

    @Column(name = "tax_amount", nullable = false, precision = 12, scale = 2)
    var taxAmount: BigDecimal,

    @Column(name = "grand_total", nullable = false, precision = 12, scale = 2)
    var grandTotal: BigDecimal,

    @Column(name = "payment_mode", length = 20)
    @Enumerated( EnumType.STRING)
    var paymentMode: PaymentMode? = null,

    @Column(name = "payment_status", length = 20)
    @Enumerated(EnumType.STRING)
    var paymentStatus: PaymentStatus? = null,

    @Column(name = "amount_paid", nullable = false, precision = 12, scale = 2)
    var amountPaid: BigDecimal = BigDecimal.ZERO,

    @Column(name = "balance_amount", nullable = false, precision = 12, scale = 2)
    var balanceAmount: BigDecimal = BigDecimal.ZERO,

    @Column(name = "customer_name", length = 100)
    var customerName: String? = null,

    @Column(name = "customer_mobile", length = 15)
    var customerMobile: String? = null,

    @Column(name = "remarks", length = 500)
    var remarks: String? = null,

    // lifecycle fields
    @Column(name = "finalized_at")
    var finalizedAt: LocalDateTime? = null,

    @Column(name = "cancelled_at")
    var cancelledAt: LocalDateTime? = null,

    @Column(name = "cancelled_by", length = 50)
    var cancelledBy: String? = null,

    @Column(name = "cancel_reason", length = 255)
    var cancelReason: String? = null,

    @OneToMany(
        mappedBy = "bill",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var items: MutableList<BillItemEntity> = mutableListOf()

) : BaseEntity() {
}