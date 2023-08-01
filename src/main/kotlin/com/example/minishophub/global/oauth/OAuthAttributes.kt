package com.example.minishophub.global.oauth

import com.example.minishophub.domain.user.persistence.SocialType
import com.example.minishophub.domain.user.persistence.SocialType.*
import com.example.minishophub.domain.user.persistence.user.User
import com.example.minishophub.domain.user.persistence.UserRole
import com.example.minishophub.global.oauth.userInfo.*
import io.github.oshai.kotlinlogging.KotlinLogging

/**
 * 각 소셜 별로 다르게 들어오는 정보를 처리하는 클래스
 */
class OAuthAttributes(
    val nameAttributeKey: String,
    val oAuth2UserInfo: OAuth2UserInfo,
) {

    /**
     * SocialType 에 맞춰서 OAuthAttribute 객체 반환
     */
    companion object {
        fun of(socialType: SocialType,
               userNameAttributeName: String,
               attributes: MutableMap<String, Any>
        ): OAuthAttributes {
            return when (socialType) {
                NAVER -> ofNaver(userNameAttributeName, attributes)
                KAKAO -> ofKakao(userNameAttributeName, attributes)
                GOOGLE -> ofGoogle(userNameAttributeName, attributes)
                NO_SOCIAL -> ofNothing(userNameAttributeName, attributes)
            }
        }


        private fun ofNaver(userNameAttributeName: String, attributes: MutableMap<String, Any>): OAuthAttributes {
            return OAuthAttributes(
                userNameAttributeName,
                NaverOAuth2UserInfo(attributes)
            )
        }

        private fun ofKakao(userNameAttributeName: String, attributes: MutableMap<String, Any>): OAuthAttributes {
            return OAuthAttributes(
                userNameAttributeName,
                KakaoOAuth2UserInfo(attributes)
            )
        }

        private fun ofGoogle(userNameAttributeName: String, attributes: MutableMap<String, Any>): OAuthAttributes {
            return OAuthAttributes(
                userNameAttributeName,
                GoogleOAuth2UserInfo(attributes)
            )
        }

        private fun ofNothing(userNameAttributeName: String, attributes: MutableMap<String, Any>):  OAuthAttributes{
            return OAuthAttributes(
                userNameAttributeName,
                NoSocialOAuth2UserInfo(attributes)
            )
        }
    }

    /**
     * ofXXX 메서드로 OAuthAttribute 객체가 생성 되었고, 유저 정보가 담긴 OAuth2UserInfo 가 주입 된 상태
     * OAuth2UserInfo 에서 정보를 가져와 User Entity 반환
     */
    fun toEntity(socialType: SocialType, oAuth2UserInfo: OAuth2UserInfo): User {

        return User(
            nickname = oAuth2UserInfo.getNickname()!!,
            socialId = oAuth2UserInfo.getId()!!,
            socialType = socialType,
            email = oAuth2UserInfo.getEmail()!!,
            role = UserRole.GUEST,
            age = -1,
            password = "NOT_NEEDED",
            city = "none",
            businessRegistrationNumber = "none",
        )
    }

}