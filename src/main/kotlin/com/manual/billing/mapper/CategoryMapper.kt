package com.manual.billing.mapper

import com.manual.billing.dto.response.CategoryResponse
import com.manual.billing.entity.CategoryEntity

fun CategoryEntity.toResponse(): CategoryResponse {
    return CategoryResponse(
        id = this.id!!,
        code = this.code,
        name = this.name,
        description = this.description,
        displayOrder = this.displayOrder,
        active = this.active
    )
}