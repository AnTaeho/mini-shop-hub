package com.example.minishophub.domain.user.controller

import com.example.minishophub.domain.item.persistence.Item
import com.example.minishophub.domain.shop.persistence.Shop
import com.example.minishophub.domain.user.persistence.user.User
import com.example.minishophub.domain.user.service.AdminService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController(
    private val adminService: AdminService,
) {

    @GetMapping("/all-user")
    fun findAllUser(): MutableList<User> = adminService.findAllUser()

    @GetMapping("/all-shop")
    fun findAllShop(): MutableList<Shop> = adminService.findAllShop()

    @GetMapping("/all-item")
    fun findAllItem(): MutableList<Item> = adminService.findAllItem()

}