package com.manual.billing.repository

import com.manual.billing.entity.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<ItemEntity, Long> {

    fun existsByItemCode(itemCode: String): Boolean

    fun findByItemCode(itemCode: String): ItemEntity?
}