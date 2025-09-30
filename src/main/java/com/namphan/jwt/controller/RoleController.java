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
        // 2. GỌI SERVICE: Controller chỉ gọi Service và chờ kết quả.
        Role savedRole = roleService.createRole(role);

        // 3. THÀNH CÔNG: Nếu không có lỗi nào được ném, Controller trả về phản hồi thành công (HTTP 201).
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
