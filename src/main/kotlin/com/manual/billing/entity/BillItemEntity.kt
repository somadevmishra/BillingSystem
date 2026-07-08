package com.manual.billing.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    name = "bill_items",
    indexes = [
        Index(name = "idx_bill_items_bill_id", columnList = "bill_id"),
        Index(name = "idx_bill_items_item_id", columnList = "item_id")
    ]
)
class BillItemEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    var bill: BillEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    var item: ItemEntity,

    // Snapshot fields
    @Column(name = "item_code", nullable = false, length = 20)
    var itemCode: String,

    @Column(name = "item_name", nullable = false, length = 255)
    var itemName: String,

    @Column(nullable = false, precision = 10, scale = 3)
    var quantity: BigDecimal,

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    var unitPrice: BigDecimal,

    @Column(name = "gst_percentage", nullable = false, precision = 5, scale = 2)
    var gstPercentage: BigDecimal = BigDecimal.ZERO,

    @Column(name = "discount_amount", nullable = false, precision = 10, scale = 2)
    var discountAmount: BigDecimal = BigDecimal.ZERO,

    @Column(name = "line_subtotal", nullable = false, precision = 12, scale = 2)
    var lineSubtotal: BigDecimal,

    @Column(name = "tax_amount", nullable = false, precision = 12, scale = 2)
    var taxAmount: BigDecimal,

    @Column(name = "line_total", nullable = false, precision = 12, scale = 2)
    var lineTotal: BigDecimal

) : BaseEntity()