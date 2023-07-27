package com.example.minishophub.global.evnet

import com.example.minishophub.domain.user.persistence.user.User

class NoticeEvent(
    val followers: MutableList<User>,
    val message: String,
) {
}