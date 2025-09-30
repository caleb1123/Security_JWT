package com.namphan.jwt.service;

import com.namphan.jwt.payload.dto.UserDto;
import com.namphan.jwt.payload.request.IntrospectRequest;
import com.namphan.jwt.payload.request.RegisterUserRequest;
import com.namphan.jwt.payload.response.IntrospectResponse;

public interface UserService {
    IntrospectResponse introspect(IntrospectRequest introspectRequest);
    UserDto register(RegisterUserRequest user);
}
