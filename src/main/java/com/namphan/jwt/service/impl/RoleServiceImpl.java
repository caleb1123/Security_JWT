package com.namphan.jwt.service.impl;

import com.namphan.jwt.payload.CreateRoleRequest;
import com.namphan.jwt.payload.entity.Role;
import com.namphan.jwt.repository.RoleRepository;
import com.namphan.jwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role createRole(CreateRoleRequest role) {
        if(roleRepository.existsByRoleName(role.getRoleName()))
        {
            throw new RuntimeException("Role already exists");
        }
        Role roleEntity = Role.builder()
                .roleName(role.getRoleName()).build();

        return roleRepository.save(roleEntity);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(int id) {
        return roleRepository.findById(id).orElse(null);
    }
}
