package com.bird.framework.system.repository;

import com.bird.framework.system.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepo extends JpaRepository<Tenant, Long> {

    Tenant findByCode(String code);
}
