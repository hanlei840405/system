package com.bird.framework.system.repository;

import com.bird.framework.system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>, QuerydslPredicateExecutor<Role> {
    Role findByCode(String code);

    Role findByTenantIdAndCode(Long tenantId, String code);

    List<Role> findByUsersId(Long userId);

    List<Role> findByTenantIdAndUsersId(Long tenantId, Long userId);
}
