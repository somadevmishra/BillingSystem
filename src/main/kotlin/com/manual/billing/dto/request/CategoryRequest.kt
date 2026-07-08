package com.manual.billing.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CategoryRequest(

    @field:NotBlank
    val code: String,

    @field:NotBlank
    val name: String,

    val description: String? = null,

    @field:NotNull
    val displayOrder: Int = 0,

    @field:NotNull
    val active: Boolean = true
)