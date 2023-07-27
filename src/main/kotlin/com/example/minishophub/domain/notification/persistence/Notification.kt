package com.example.minishophub.domain.notification.persistence

import com.example.minishophub.domain.user.persistence.user.User
import jakarta.persistence.*

@Entity
class Notification (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    var id: Long = 0L,

    var message: String,
    var isChecked: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var notifiedUser: User,
)


