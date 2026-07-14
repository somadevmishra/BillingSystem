package com.manual.billing.service

interface SequenceService{
    fun nextItemCode(): String

    fun nextBillNumber(): String

    fun nextCategoryNumber(): String
}