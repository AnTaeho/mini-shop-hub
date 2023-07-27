package com.example.minishophub.domain.notification.service

import com.example.minishophub.domain.notification.persistence.Notification
import com.example.minishophub.domain.notification.persistence.NotificationRepository
import com.example.minishophub.domain.user.persistence.user.UserRepository
import com.example.minishophub.global.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val userRepository: UserRepository,
) {

    fun findMyNotification(email: String): MutableList<Notification> {
        val user = userRepository.findByEmail(email) ?: fail()
        return notificationRepository.findByNotifiedUser(user)
    }

    @Transactional
    fun readAll(email: String) {
        val notifications = findMyNotification(email)
        notifications.forEach {
            it.isChecked = true
        }
    }

}