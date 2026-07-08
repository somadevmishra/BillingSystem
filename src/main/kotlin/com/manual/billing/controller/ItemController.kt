package com.manual.billing.controller

import com.manual.billing.dto.request.CreateItemRequest
import com.manual.billing.dto.request.UpdateItemRequest
import com.manual.billing.dto.response.ItemResponse
import com.manual.billing.service.ItemService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/items")
class ItemController(
    private val itemService: ItemService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createItem(
        @Valid @RequestBody request: CreateItemRequest
    ): ItemResponse =
        itemService.createItem(request)

    @GetMapping("/{id}")
    fun getItemById(
        @PathVariable id: Long
    ): ItemResponse =
        itemService.getItemById(id)

    @GetMapping
    fun getAllItems(): List<ItemResponse> =
        itemService.getAllItems()

    @PutMapping("/{id}")
    fun updateItem(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateItemRequest
    ): ItemResponse =
        itemService.updateItem(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteItem(
        @PathVariable id: Long
    ) {
        itemService.deleteItem(id)
    }
}