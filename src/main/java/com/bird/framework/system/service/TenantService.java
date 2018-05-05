package com.bird.framework.system.service;

import com.bird.framework.system.entity.Tenant;
import com.bird.framework.system.repository.TenantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService {
    @Autowired
    private TenantRepo tenantRepo;

    public Tenant get(Long id) {
        return tenantRepo.getOne(id);
    }

    public Tenant getByCode(String code) {
        return tenantRepo.findByCode(code);
    }
}
