package com.manual.billing.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UpdateCategoryRequest(

    @field:NotBlank
    val name: String,

    val description: String? = null,

    @field:NotNull
    val displayOrder: Int,

    @field:NotNull
    val active: Boolean
)