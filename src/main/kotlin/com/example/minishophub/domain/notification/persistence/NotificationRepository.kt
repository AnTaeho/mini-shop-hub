package com.example.minishophub.domain.notification.persistence

import com.example.minishophub.domain.user.persistence.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findByNotifiedUser(notifiedUser: User): MutableList<Notification>
}