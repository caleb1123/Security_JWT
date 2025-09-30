package com.namphan.jwt.controller;

import com.namphan.jwt.payload.CreateRoleRequest;
import com.namphan.jwt.payload.entity.Role;
import com.namphan.jwt.payload.response.ApiResponse;
import com.namphan.jwt.repository.RoleRepository;
import com.namphan.jwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    // Tạo mới role
    @PostMapping
    public ResponseEntity<ApiResponse<Role>> createRole(@RequestBody CreateRoleRequest role) {
        Role savedRole = roleService.createRole(role);
        ApiResponse<Role> response = ApiResponse.<Role>builder()
                .code(HttpStatus.CREATED.value())
                .message("Role created successfully")
                .data(savedRole)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Lấy tất cả roles
    @GetMapping
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        ApiResponse<List<Role>> response = ApiResponse.<List<Role>>builder()
                .code(HttpStatus.OK.value())
                .message("Fetched all roles successfully")
                .data(roles)
                .build();
        return ResponseEntity.ok(response);
    }

    // Lấy role theo id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Role>> getRoleById(@PathVariable int id) {
        Role role = roleService.getRoleById(id);
        ApiResponse<Role> response = ApiResponse.<Role>builder()
                .code(HttpStatus.OK.value())
                .message("Fetched role successfully")
                .data(role)
                .build();
        return ResponseEntity.ok(response);
    }

}
