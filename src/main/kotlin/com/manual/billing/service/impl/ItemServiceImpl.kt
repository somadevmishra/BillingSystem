package com.manual.billing.service.impl

import com.manual.billing.dto.request.CreateItemRequest
import com.manual.billing.dto.request.UpdateItemRequest
import com.manual.billing.dto.response.ItemResponse
import com.manual.billing.entity.ItemEntity
import com.manual.billing.exception.ResourceNotFoundException
import com.manual.billing.mapper.toResponse
import com.manual.billing.repository.ItemRepository
import com.manual.billing.service.ItemService
import com.manual.billing.service.SequenceService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class ItemServiceImpl(
    private val itemRepository: ItemRepository,
    private val sequenceService: SequenceService
) : ItemService {

    override fun createItem(
        request: CreateItemRequest
    ): ItemResponse {

        val item = ItemEntity(
            itemCode = sequenceService.nextItemCode(),
            name = request.name,
            description = request.description,
            category = request.category,
            unitPrice = request.unitPrice,
            gstPercentage = request.gstPercentage
        )

        return itemRepository.save(item).toResponse()
    }

    @Transactional(readOnly = true)
    override fun getItemById(id: Long): ItemResponse =
        itemRepository.findById(id)
            .orElseThrow {
                ResourceNotFoundException("Item not found with id $id")
            }
            .toResponse()

    @Transactional(readOnly = true)
    override fun getAllItems(): List<ItemResponse> =
        itemRepository.findAll().stream()
            .map { it.toResponse() }.toList()

    override fun updateItem(
        id: Long,
        request: UpdateItemRequest
    ): ItemResponse {

        val item = itemRepository.findById(id)
            .orElseThrow {
                ResourceNotFoundException("Item not found with id $id")
            }

        item.name = request.name
        item.description = request.description
        item.category = request.category
        item.unitPrice = request.unitPrice
        item.gstPercentage = request.gstPercentage
        item.active = request.active
        item.updatedAt = LocalDateTime.now()

        return itemRepository.save(item).toResponse()
    }

    override fun deleteItem(id: Long) {

        val item = itemRepository.findById(id)
            .orElseThrow {
                ResourceNotFoundException("Item not found with id $id")
            }

        item.active = false
        item.updatedAt = LocalDateTime.now()

        itemRepository.save(item)
    }
}