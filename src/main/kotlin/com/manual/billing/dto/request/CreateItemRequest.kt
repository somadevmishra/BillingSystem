package com.manual.billing.dto.request

import com.manual.billing.entity.CategoryEntity
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

data class CreateItemRequest (

    @field:NotBlank
    val itemCode: String,

    @field:NotBlank
    val name: String,

    val description: String? = null,

    val category: CategoryEntity? = null,

    @field:DecimalMin("0.00")
    val unitPrice: BigDecimal,

    @field:DecimalMin("0.00")
    val gstPercentage: BigDecimal = BigDecimal.ZERO
)

