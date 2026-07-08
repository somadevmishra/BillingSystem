package com.manual.billing.repository

import com.manual.billing.entity.BillEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BillRepository : JpaRepository<BillEntity, Long> {

    fun findByBillNo(billNo: String): BillEntity?

    fun findAllByOrderByBillDateDesc(): List<BillEntity>
}