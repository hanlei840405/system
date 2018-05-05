package com.bird.framework.system.repository;

import com.bird.framework.system.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Long> {

    Organization findByTenantIdAndId(Long tenantId, Long id);

    Organization findByTenantIdAndCode(Long tenantId, String code);

    List<Organization> findByTenantIdAndNameStartingWith(Long tenantId, String name);

    List<Organization> findByTenantIdAndPathStartingWith(Long tenantId, String path);
}
