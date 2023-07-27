package com.example.minishophub.global.evnet

import com.example.minishophub.domain.user.persistence.follow.Follow

data class NoticeEvent(
    val followers: MutableList<Follow>,
    val message: String,
)