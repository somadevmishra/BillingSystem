package com.manual.billing.service

import com.manual.billing.dto.request.CreateCategoryRequest
import com.manual.billing.dto.request.UpdateCategoryRequest
import com.manual.billing.dto.response.CategoryResponse

interface CategoryService {

    fun createCategory(request: CreateCategoryRequest): CategoryResponse

    fun updateCategory(id: Long, request: UpdateCategoryRequest): CategoryResponse

    fun getCategoryById(id: Long): CategoryResponse

    fun getAllCategories(): List<CategoryResponse>

    fun deleteCategory(id: Long)
}