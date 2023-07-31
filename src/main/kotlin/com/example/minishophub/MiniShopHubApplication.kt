package com.example.minishophub

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class MiniShopHubApplication

fun main(args: Array<String>) {
    runApplication<MiniShopHubApplication>(*args)
}
