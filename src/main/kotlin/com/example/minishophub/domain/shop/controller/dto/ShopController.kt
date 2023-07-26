package com.example.minishophub.domain.shop.controller.dto

import com.example.minishophub.domain.shop.controller.dto.request.ShopRegisterRequest
import com.example.minishophub.domain.shop.persistence.Shop
import com.example.minishophub.domain.shop.service.ShopService
import com.example.minishophub.global.jwt.service.JwtService
import jakarta.servlet.http.HttpServletRequest
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
    private val jwtService: JwtService,
) {

    @PostMapping
    fun registerShop(@RequestBody registerRequest: ShopRegisterRequest,
                     request: HttpServletRequest
    ): Shop {
        val accessToken = jwtService.extractAccessToken(request) ?: throw IllegalArgumentException()
        val email = jwtService.extractEmail(accessToken)
        return shopService.registerShop(registerRequest, email!!)
    }

    @GetMapping("/{shopId}")
    fun findShop1(@PathVariable shopId: Long): Shop = shopService.findShop(shopId)

    @GetMapping("/owner/{bizNumber}")
    fun findShop2(@PathVariable bizNumber: String): Shop = shopService.findShop(bizNumber)

    @DeleteMapping("/owner/{bizNumber}")
    fun deleteShop(@PathVariable bizNumber: String) = shopService.deleteShop(bizNumber)

}