package com.manual.billing.controller

import com.manual.billing.dto.request.CreateCategoryRequest
import com.manual.billing.dto.request.UpdateCategoryRequest
import com.manual.billing.dto.response.CategoryResponse
import com.manual.billing.service.CategoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryService: CategoryService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCategory(
        @Valid @RequestBody request: CreateCategoryRequest
    ): CategoryResponse =
        categoryService.createCategory(request)

    @GetMapping("/{id}")
    fun getCategoryById(
        @PathVariable id: Long
    ): CategoryResponse =
        categoryService.getCategoryById(id);

    @GetMapping
    fun getAllCategories(): List<CategoryResponse> =
        categoryService.getAllCategories()

    @PutMapping("/{id}")
    fun updateCategory(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateCategoryRequest
    ): CategoryResponse =
        categoryService.updateCategory(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCategory(
        @PathVariable id: Long
    ) {
        categoryService.deleteCategory(id)
    }
}