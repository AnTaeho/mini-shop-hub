package com.example.minishophub.domain.item.controller

import com.example.minishophub.domain.item.controller.dto.request.ItemRegisterRequest
import com.example.minishophub.domain.item.persistence.Category
import com.example.minishophub.domain.item.persistence.Item
import com.example.minishophub.domain.item.service.ItemService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/item")
class ItemController(
    private val itemService: ItemService,
) {

    @PostMapping("/{shopId}")
    fun registerItem(@RequestBody registerRequest: ItemRegisterRequest,
                     @PathVariable shopId: Long,
    ): Item  = itemService.registerItem(registerRequest, shopId)

    @GetMapping("/search")
    fun searchCategory(@RequestBody category: Category): MutableList<Item> = itemService.searchCategory(category)

    @GetMapping("/{itemId}")
    fun findItem(@PathVariable itemId: Long): Item = itemService.findItem(itemId)

    @PutMapping("/plus/{itemId}")
    fun reStock(@PathVariable itemId: Long,
                @RequestBody count: Int) = itemService.reStockItem(itemId, count)

    @PutMapping("/minus/{itemId}")
    fun sell(@PathVariable itemId: Long,
                @RequestBody count: Int) = itemService.sellItem(itemId, count)

    @DeleteMapping("/{itemId}")
    fun deleteItem(@PathVariable itemId: Long) = itemService.deleteItem(itemId)
}