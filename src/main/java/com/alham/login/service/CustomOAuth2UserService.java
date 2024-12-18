package com.alham.login.service;

import com.alham.login.dto.OAuth2SaveUser;
import com.alham.login.dto.OAuth2UserInfo;
import com.alham.login.dto.PrincipalDetails;
import com.alham.login.repository.OAuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.DelegatingOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final OAuthUserRepository oAuthUserRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //유저 정보 가져오기
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

        //registrationId 가져오기
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //userNameAttributeName 가져오기
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        //유저정보 DTO 생성
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2UserAttributes);

        //회원가입 및 로그인
        OAuth2SaveUser saveUser = getOrSave(oAuth2UserInfo);

        return new PrincipalDetails(saveUser, oAuth2UserAttributes, userNameAttributeName);
    }

    private OAuth2SaveUser getOrSave(OAuth2UserInfo oAuth2UserInfo) {
        OAuth2SaveUser member = oAuthUserRepository.findByEmail(oAuth2UserInfo.email())
                .orElseGet(oAuth2UserInfo::toEntity);
        return oAuthUserRepository.save(member);
    }
}
