package com.example.minishophub.global.oauth

import com.example.minishophub.domain.user.persistence.SocialType
import com.example.minishophub.domain.user.persistence.User
import com.example.minishophub.domain.user.persistence.UserRole
import com.example.minishophub.global.oauth.userInfo.GoogleOAuth2UserInfo
import com.example.minishophub.global.oauth.userInfo.KakaoOAuth2UserInfo
import com.example.minishophub.global.oauth.userInfo.NaverOAuth2UserInfo
import com.example.minishophub.global.oauth.userInfo.OAuth2UserInfo
import java.util.UUID

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
                SocialType.NAVER -> ofNaver(userNameAttributeName, attributes)
                SocialType.KAKAO -> ofKakao(userNameAttributeName, attributes)
                SocialType.GOOGLE -> ofGoogle(userNameAttributeName, attributes)
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
            city = "none"
        )
    }

}