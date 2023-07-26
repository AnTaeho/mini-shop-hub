package com.example.minishophub.domain.item.controller.dto.request

import com.example.minishophub.domain.item.persistence.Category

data class ItemRegisterRequest(
    val name: String,
    val image: String,
    val category: Category,
    val quantity: Int,
)