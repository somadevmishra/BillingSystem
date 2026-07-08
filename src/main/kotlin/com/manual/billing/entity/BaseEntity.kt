package com.manual.billing.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Version
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    var createdBy: String? = null

    var updatedBy: String? = null

    @Version
    var version: Long? = null
}