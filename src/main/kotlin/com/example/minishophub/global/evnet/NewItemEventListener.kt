package com.example.minishophub.global.evnet

import com.example.minishophub.domain.notification.persistence.Notification
import com.example.minishophub.domain.notification.persistence.NotificationRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NewItemEventListener(
    private val notificationRepository: NotificationRepository,
) {

    private val log = KotlinLogging.logger { }

    @EventListener
    @Transactional
    fun sendNotification(event: NewItemEvent) {
        log.info { "알람 생성 시작" }
        event.followers.forEach {
            notificationRepository.save(
                Notification(
                    message = event.message,
                    notifiedUser = it.user
                )
            )
        }
        log.info { "알람 생성 종료" }
    }
}