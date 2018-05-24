package com.bird.framework.system.repository;

import com.bird.framework.system.entity.Menu;
import com.bird.framework.system.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepo extends JpaRepository<Menu, Long>, QuerydslPredicateExecutor<Menu> {
    Menu findByCode(String code);

    Menu findByTenantIdAndCode(Long tenantId, String code);

    List<Menu> findByRolesId(Long roleId);
}
