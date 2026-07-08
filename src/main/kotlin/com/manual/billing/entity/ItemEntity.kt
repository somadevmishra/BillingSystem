package com.manual.billing.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "items")
class ItemEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true, length = 20)
    var itemCode: String,

    @Column(nullable = false, length = 255)
    var name: String,

    @Column(length = 500)
    var description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: CategoryEntity? = null,

    @Column(nullable = false, precision = 10, scale = 2)
    var unitPrice: BigDecimal,

    @Column(nullable = false, precision = 5, scale = 2)
    var gstPercentage: BigDecimal = BigDecimal.ZERO,

    var displayOrder: Int = 0,

    var active: Boolean = true
) : BaseEntity()