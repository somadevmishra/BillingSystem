package com.manual.billing.service.impl

import com.manual.billing.repository.CodeSequenceRepository
import com.manual.billing.service.SequenceService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SequenceServiceImpl(
    private val repository: CodeSequenceRepository
) : SequenceService {
    @Transactional
    override fun nextItemCode(): String {
        val sequence = repository.findForUpdate("ITEM")
            ?: throw IllegalStateException("ITEM sequence not configured")
        val next = sequence.nextValue
        sequence.nextValue = next + 1
        return "ITM-$next"
    }

    override fun nextBillNumber(): String {
        val sequence = repository.findForUpdate("BILL")
            ?: throw IllegalStateException("Billing sequence not configured")
        val next = sequence.nextValue
        sequence.nextValue = next + 1
        return "BIL-$next"
    }
}