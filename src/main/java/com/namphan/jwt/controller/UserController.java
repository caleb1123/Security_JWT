package com.namphan.jwt.controller;

import com.namphan.jwt.payload.dto.UserDto;
import com.namphan.jwt.payload.entity.Role;
import com.namphan.jwt.payload.request.RegisterUserRequest;
import com.namphan.jwt.payload.response.ApiResponse;
import com.namphan.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> registerUser(@RequestBody RegisterUserRequest userRequest) {
        UserDto userDto = userService.register(userRequest);

        ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                .code(HttpStatus.CREATED.value())
                .message("User register successfully")
                .data(userDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
