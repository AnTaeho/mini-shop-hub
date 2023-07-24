package com.example.minishophub.domain.base

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity (

    @Column(updatable = false)
    @CreatedDate
    private var createdDate: LocalDateTime? = null,

    @LastModifiedDate
    private var lastModifiedDate: LocalDateTime? = null,
)
