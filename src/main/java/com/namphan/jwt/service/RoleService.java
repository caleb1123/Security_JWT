package com.namphan.jwt.service;

import com.namphan.jwt.payload.CreateRoleRequest;
import com.namphan.jwt.payload.entity.Role;

import java.util.List;

public interface RoleService {
    Role createRole(CreateRoleRequest role);
    List<Role> getAllRoles();
    Role getRoleById(int id);
}
