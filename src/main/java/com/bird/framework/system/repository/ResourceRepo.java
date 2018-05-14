package com.bird.framework.system.repository;

import com.bird.framework.system.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepo extends JpaRepository<Resource, Long> {
    Resource findByCode(String code);

    Resource findByTenantIdAndCode(Long tenantId, String code);

    List<Resource> findByRolesId(Long roleId);

    List<Resource> findByTenantIdAndRolesId(Long tenantId, Long roleId);
}
