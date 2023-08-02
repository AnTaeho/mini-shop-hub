package com.example.minishophub.global.oauth.userInfo

class NaverOAuth2UserInfo(attributes: MutableMap<String, Any>) : OAuth2UserInfo(attributes) {
    override fun getId(): String? {
        val response = attributes["response"] as Map<*, *>? ?: return null

        return (response["id"] as String?)!!
    }

    override fun getNickname(): String? {
        val response = attributes["response"] as Map<*, *>? ?: return null

        return (response["nickname"] as String?)!!
    }

    override fun getEmail(): String? {
        val response = attributes["response"] as Map<*, *>? ?: return null
        return (response["email"] as String?)!!
    }

    override fun getImageUrl(): String? {
        val response = attributes["response"] as Map<*, *>? ?: return null
        return (response["profile_image"] as String?)!!
    }
}