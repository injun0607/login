package com.alham.login.dto;

import lombok.Builder;

import java.util.Map;
//TODO - 직접 디버깅해보면서 수정필요
@Builder
public record OAuth2UserInfo(String name, String email, String profile) {
    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case "google" -> ofGoogle(attributes);
            case "naver" -> ofNaver(attributes);
            case "kakao" -> ofKakao(attributes);
            default -> throw new IllegalArgumentException("Unsupported registrationId: " + registrationId);
        };
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("picture"))
                .build();
    }

    private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuth2UserInfo.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .profile((String) response.get("profile_image"))
                .build();
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        return OAuth2UserInfo.builder()
                .name((String) profile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .profile((String) profile.get("profile_image_url"))
                .build();
    }

    public OAuth2SaveUser toEntity() {
        return OAuth2SaveUser.builder()
                .name(name)
                .email(email)
                .build();
    }
}
