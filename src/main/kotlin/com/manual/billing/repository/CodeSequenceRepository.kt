package com.manual.billing.repository

import com.manual.billing.entity.CodeSequenceEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface CodeSequenceRepository :
    JpaRepository<CodeSequenceEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(
        "select s from CodeSequenceEntity s where s.sequenceName = :name"
    )
    fun findForUpdate(name: String): CodeSequenceEntity?
}