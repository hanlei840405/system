package com.bird.framework.system.service;

import com.bird.framework.system.entity.QRole;
import com.bird.framework.system.entity.Role;
import com.bird.framework.system.repository.RoleRepo;
import com.bird.framework.system.vo.RoleVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    public List<RoleVo> findByUserId(Long userId) {
        List<Role> roles = roleRepo.findByUsersId(userId);
        List<RoleVo> roleVos = new ArrayList<>();
        roles.forEach(role -> {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(role, roleVo);
            roleVos.add(roleVo);
        });
        return roleVos;
    }

    public List<Role> findByUserId(Long tenantId, Long userId) {
        return roleRepo.findByTenantIdAndUsersId(tenantId, userId);
    }

    public Page<Role> page(Long tenantId, String code, int pageNo, int pageSize) {
        QRole qRole = QRole.role;
        BooleanExpression booleanExpression = null;
        if (tenantId != null) {
            if (booleanExpression == null) {
                booleanExpression = qRole.tenant.id.eq(tenantId);
            } else {
                booleanExpression.and(qRole.tenant.id.eq(tenantId));
            }
        }
        if (!StringUtils.isEmpty(code)) {
            if (booleanExpression == null) {
                booleanExpression = qRole.code.eq(code);
            } else {
                booleanExpression.and(qRole.code.eq(code));
            }
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Role> roles = roleRepo.findAll(booleanExpression, pageable);

        return roles;
    }

    public Role save(Role role) {
        return roleRepo.save(role);
    }
}