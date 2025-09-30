package com.namphan.jwt.payload.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResquest {
    private String username;
    private String password;
}
