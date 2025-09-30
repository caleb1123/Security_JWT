package com.namphan.jwt.payload.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiry;   // thời gian hết hạn access token (timestamp)
    private Long refreshTokenExpiry;  // thời gian hết hạn refresh token (timestamp)
}
