package com.manual.billing.repository

import com.manual.billing.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<CategoryEntity, Long> {

    fun existsByNameIgnoreCase(name: String): Boolean

    fun findByNameIgnoreCase(name: String): CategoryEntity?

    fun findAllByOrderByDisplayOrderAscNameAsc(): List<CategoryEntity>

    fun findAll(activeOnly: Boolean = false): List<CategoryEntity>
}