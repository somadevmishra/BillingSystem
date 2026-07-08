package com.manual.billing.entity

import jakarta.persistence.*

@Entity
@Table(name = "categories")
class CategoryEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true, length = 20)
    var code: String,

    @Column(nullable = false, length = 100)
    var name: String,

    var description: String? = null,

    var displayOrder: Int = 0,

    var active: Boolean = true
): BaseEntity()