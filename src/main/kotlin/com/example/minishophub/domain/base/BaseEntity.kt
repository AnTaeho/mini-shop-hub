package com.example.minishophub.domain.base

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseEntity(
    @CreatedDate
    var createdAt: LocalDateTime? = null,
    @LastModifiedDate
    var modifiedAt: LocalDateTime? = null,
    var visibility: Boolean = true
) {
    fun softDelete() {
        visibility = false
    }
}
