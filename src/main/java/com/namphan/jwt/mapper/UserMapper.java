package com.namphan.jwt.mapper;

import com.namphan.jwt.payload.dto.UserDto;
import com.namphan.jwt.payload.entity.User;
import com.namphan.jwt.payload.request.RegisterUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUser(RegisterUserRequest request);

    @Mapping(source = "role.roleName", target = "roleName") // <-- FIX Ở ĐÂY
    @Mapping(target = "password", ignore = true)       // <-- KHUYẾN NGHỊ BẢO MẬT
    UserDto toUserDto(User entity);
}
