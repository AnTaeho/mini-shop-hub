package com.example.minishophub.domain.shop.controller.dto

import com.example.minishophub.domain.shop.controller.dto.request.ShopRegisterRequest
import com.example.minishophub.domain.shop.persistence.Shop
import com.example.minishophub.domain.shop.service.ShopService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/shop")
class ShopController(
    private val shopService: ShopService,
) {

    //TODO 유저 탐색 방식 변경 & 유저 상태 (셀러/바이어) 분리 예정
    @PostMapping("/{userId}")
    fun registerShop(@RequestBody registerRequest: ShopRegisterRequest,
                     @PathVariable userId: Long,
    ): Shop = shopService.registerShop(registerRequest, userId)

    @GetMapping("/{shopId}")
    fun findShop1(@PathVariable shopId: Long): Shop = shopService.findShop(shopId)

    @GetMapping("/owner/{bizNumber}")
    fun findShop2(@PathVariable bizNumber: String): Shop = shopService.findShop(bizNumber)

    @DeleteMapping("/owner/{bizNumber}")
    fun deleteShop(@PathVariable bizNumber: String) = shopService.deleteShop(bizNumber)

}