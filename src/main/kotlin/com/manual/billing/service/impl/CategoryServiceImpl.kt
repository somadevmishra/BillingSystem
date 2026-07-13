package com.manual.billing.service.impl

import com.manual.billing.dto.request.CreateCategoryRequest
import com.manual.billing.dto.request.UpdateCategoryRequest
import com.manual.billing.dto.response.CategoryResponse
import com.manual.billing.entity.CategoryEntity
import com.manual.billing.exception.ResourceNotFoundException
import com.manual.billing.mapper.toResponse
import com.manual.billing.repository.CategoryRepository
import com.manual.billing.service.CategoryService
import com.manual.billing.service.SequenceService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
    private val sequenceService: SequenceService
): CategoryService {
    override fun createCategory(request: CreateCategoryRequest): CategoryResponse {
        check(!categoryRepository.existsByNameIgnoreCase(request.name)) {
            "Category '${request.name}' already exists."
        }

        val entity = CategoryEntity(
            name = request.name.trim(),
            description = request.description,
            displayOrder = request.displayOrder,
            code = sequenceService.nextCategoryNumber()
        )

        return categoryRepository.save(entity).toResponse()
    }

    override fun updateCategory(id: Long, request: UpdateCategoryRequest): CategoryResponse {
        val category = categoryRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Category: {}no ") }

        if (!category.name.equals(request.name, true)
            && categoryRepository.existsByNameIgnoreCase(request.name)
        ) {
            throw IllegalArgumentException("Category already exists.")
        }

        category.name = request.name.trim()
        category.description = request.description
        category.displayOrder = request.displayOrder
        category.active = request.active

        return categoryRepository.save(category).toResponse()
    }

    @Transactional(readOnly = true)
    override fun getCategoryById(id: Long): CategoryResponse =
        categoryRepository.findById(id)
            .orElseThrow{ ResourceNotFoundException("Category not found with id $id")}
            .toResponse();

    @Transactional(readOnly = true)
    override fun getAllCategories(): List<CategoryResponse> =
        categoryRepository.findAll().map {item -> item.toResponse()}.toList()


    override fun deleteCategory(id: Long) {
        val category = categoryRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Category  not found with id $id") }

        category.active = false

        categoryRepository.save(category)
    }
}