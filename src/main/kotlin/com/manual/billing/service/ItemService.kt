package com.manual.billing.service

import com.manual.billing.dto.request.CreateItemRequest
import com.manual.billing.dto.request.UpdateItemRequest
import com.manual.billing.dto.response.ItemResponse

interface ItemService {

    fun createItem(request: CreateItemRequest): ItemResponse

    fun getItemById(id: Long): ItemResponse

    fun getAllItems(): List<ItemResponse>

    fun updateItem(id: Long, request: UpdateItemRequest): ItemResponse

    fun deleteItem(id: Long)
}