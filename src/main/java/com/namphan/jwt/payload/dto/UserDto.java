package com.namphan.jwt.payload.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private int id;
    private String username;
    private String password;
    private String email;
    private String roleName;
}
