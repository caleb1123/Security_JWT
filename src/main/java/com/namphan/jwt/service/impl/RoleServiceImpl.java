package com.namphan.jwt.service.impl;

import com.namphan.jwt.exception.AppException;
import com.namphan.jwt.exception.ErrorCode;
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
            // 1. NÉM LỖI: Nếu tên role đã tồn tại, Service sẽ ném AppException.
            //    Lỗi này mang theo ErrorCode.USER_EXISTED (mặc dù nên là ROLE_EXISTED).
            throw new AppException(ErrorCode.USER_EXISTED);
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
