package com.example.minishophub.util

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull


fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
    return this.findByIdOrNull(id) ?: fail()
}

fun fail(): Nothing {
    throw IllegalArgumentException("존재하지 않는 유저 입니다.")
}