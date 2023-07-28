package com.example.minishophub.domain.item.controller

import com.example.minishophub.domain.item.controller.dto.request.ItemRegisterRequest
import com.example.minishophub.domain.item.persistence.Category
import com.example.minishophub.domain.item.persistence.Item
import com.example.minishophub.domain.item.service.ItemService
import org.springframework.web.bind.annotation.*

@RestController
class ItemController(
    private val itemService: ItemService,
) {

    @PostMapping("/owner/{shopId}")
    fun registerItem(@RequestBody registerRequest: ItemRegisterRequest,
                     @PathVariable shopId: Long,
    ): Item  = itemService.registerItem(registerRequest, shopId)

    @GetMapping("/item/search")
    fun searchCategory(@RequestBody category: Category): MutableList<Item> = itemService.searchCategory(category)

    @GetMapping("/item/{itemId}")
    fun findItem(@PathVariable itemId: Long): Item = itemService.findItem(itemId)

    @PutMapping("/owner/plus/{itemId}")
    fun reStock(@PathVariable itemId: Long,
                @RequestBody count: Int) = itemService.reStockItem(itemId, count)

    @PutMapping("/owner/minus/{itemId}")
    fun sell(@PathVariable itemId: Long,
                @RequestBody count: Int) = itemService.sellItem(itemId, count)

    @DeleteMapping("/{itemId}")
    fun deleteItem(@PathVariable itemId: Long) = itemService.deleteItem(itemId)
}