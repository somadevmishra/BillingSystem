package com.manual.billing.mapper

import com.manual.billing.dto.response.ItemResponse
import com.manual.billing.entity.ItemEntity

fun ItemEntity.toResponse(): ItemResponse {
    return ItemResponse(
        id = this.id!!,
        itemCode = this.itemCode,
        name = this.name,
        description = this.description,
        category = this.category?.toResponse(),
        unitPrice = this.unitPrice,
        gstPercentage = this.gstPercentage,
        active = this.active
    )
}