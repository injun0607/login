package com.alham.login.repository;

import com.alham.login.dto.OAuth2SaveUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class OAuthUserRepositoryImpl implements OAuthUserRepository{
    @Override
    public Optional<OAuth2SaveUser> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public OAuth2SaveUser save(OAuth2SaveUser member) {
        return null;
    }
}
