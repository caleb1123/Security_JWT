package com.namphan.jwt.service.impl;

import com.namphan.jwt.exception.AppException;
import com.namphan.jwt.exception.ErrorCode;
import com.namphan.jwt.jwt.Jwt;
import com.namphan.jwt.mapper.UserMapper;
import com.namphan.jwt.payload.dto.UserDto;
import com.namphan.jwt.payload.entity.Role;
import com.namphan.jwt.payload.entity.User;
import com.namphan.jwt.payload.request.IntrospectRequest;
import com.namphan.jwt.payload.request.LoginResquest;
import com.namphan.jwt.payload.request.RegisterUserRequest;
import com.namphan.jwt.payload.response.IntrospectResponse;
import com.namphan.jwt.payload.response.LoginResponse;
import com.namphan.jwt.repository.RoleRepository;
import com.namphan.jwt.repository.UserRepository;
import com.namphan.jwt.service.UserService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    Jwt jwt;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) {
        var token = introspectRequest.getToken();
        boolean isValid = true;

        try {
            // Kiểm tra tính hợp lệ của token
            jwt.verifyToken(token);
        } catch (AppException e) {
            // Xử lý lỗi AppException
            isValid = false;
        } catch (JOSEException e) {
            // Xử lý lỗi JOSEException
            isValid = false;
        } catch (ParseException e) {
            // Xử lý lỗi ParseException
            isValid = false;
        } catch (Exception e) {
            // Bắt tất cả các lỗi không xác định
            isValid = false;
        }

        // Trả về IntrospectResponse với trạng thái valid và thông báo lỗi nếu có
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Override
    public UserDto register(RegisterUserRequest user) {
        if(userRepository.existsUserByUsername(user.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Role role = roleRepository.findById(user.getRoleId()).orElse(null);
        if(role == null) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User userEntity = User.builder()
                .username(user.getUsername())
                .password(encodedPassword)
                .email(user.getEmail())
                .role(role)
                .build();

        userRepository.save(userEntity);
        return  userMapper.toUserDto(userEntity);
    }

    @Override
    public LoginResponse login(LoginResquest loginRequest) {
        User user = userRepository.findUserByUsername(loginRequest.getUsername());
        if(user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }


        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }


        Jwt.TokenPair tokenPair = jwt.generateTokens(user);

        return LoginResponse.builder()
                .accessToken(tokenPair.accessToken().token())
                .refreshToken(tokenPair.refreshToken().token())
                .accessTokenExpiry(tokenPair.accessToken().expiryDate().getTime())
                .refreshTokenExpiry(tokenPair.refreshToken().expiryDate().getTime())
                .build();
    }
}
