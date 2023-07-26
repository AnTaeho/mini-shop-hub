package com.example.minishophub.domain.shop.controller.dto

import com.example.minishophub.domain.shop.controller.dto.request.ShopRegisterRequest
import com.example.minishophub.domain.shop.persistence.Shop
import com.example.minishophub.domain.shop.service.ShopService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ShopController(
    private val shopService: ShopService,
) {

    @PostMapping("/owner/shop")
    fun registerShop(@RequestBody registerRequest: ShopRegisterRequest,
                     @AuthenticationPrincipal userDetails: UserDetails,
    ): Shop {
        val email = userDetails.username
        return shopService.registerShop(registerRequest, email!!)
    }

    @GetMapping("/shop/{shopId}")
    fun findShop1(@PathVariable shopId: Long): Shop = shopService.findShop(shopId)

    @GetMapping("/owner/{bizNumber}")
    fun findShop2(@PathVariable bizNumber: String): Shop = shopService.findShop(bizNumber)

    @DeleteMapping("/owner/{bizNumber}")
    fun deleteShop(@PathVariable bizNumber: String) = shopService.deleteShop(bizNumber)

}