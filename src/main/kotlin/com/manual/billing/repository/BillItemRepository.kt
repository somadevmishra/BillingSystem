package com.manual.billing.repository

import com.manual.billing.entity.BillItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BillItemRepository:JpaRepository<BillItemEntity, Long> {
}