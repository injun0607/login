package com.alham.login.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuth2SaveUser {

    private String name;
    private String email;

}
