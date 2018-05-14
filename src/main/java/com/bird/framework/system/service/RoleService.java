package com.bird.framework.system.service;

import com.bird.framework.system.entity.Role;
import com.bird.framework.system.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    public Role get(Long id) {
        return roleRepo.getOne(id);
    }

    public Role getByCode(String code) {
        return roleRepo.findByCode(code);
    }

    public List<Role> findByUserId(Long userId) {
        return roleRepo.findByUsersId(userId);
    }

    public List<Role> findByUserId(Long tenantId, Long userId) {
        return roleRepo.findByTenantIdAndUsersId(tenantId, userId);
    }

    public void save(Role role) {
        roleRepo.save(role);
    }
}
