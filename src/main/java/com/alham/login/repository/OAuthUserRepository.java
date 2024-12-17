package com.alham.login.repository;

import com.alham.login.dto.OAuth2SaveUser;

import java.util.Optional;

public interface OAuthUserRepository {
    Optional<OAuth2SaveUser> findByEmail(String email);
    OAuth2SaveUser save(OAuth2SaveUser member);
}
