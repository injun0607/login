package com.alham.login.repository;

import com.alham.login.dto.OAuth2SaveUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthUserRepository {
    Optional<OAuth2SaveUser> findByEmail(String email);
    OAuth2SaveUser save(OAuth2SaveUser member);
}
