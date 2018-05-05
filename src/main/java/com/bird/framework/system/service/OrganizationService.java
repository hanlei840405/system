package com.bird.framework.system.service;

import com.bird.framework.system.entity.Organization;
import com.bird.framework.system.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepo organizationRepo;

    public Organization get(Long id) {
        return organizationRepo.getOne(id);
    }

    public Organization get(Long tenantId, Long id) {
        return organizationRepo.findByTenantIdAndId(tenantId, id);
    }

    public Organization getByCode(Long tenantId, String code) {
        return organizationRepo.findByTenantIdAndCode(tenantId, code);
    }

    public List<Organization> findByPath(Long tenantId, String path) {
        return organizationRepo.findByTenantIdAndPathStartingWith(tenantId, path);
    }

    public List<Organization> findByName(Long tenantId, String name) {
        return organizationRepo.findByTenantIdAndNameStartingWith(tenantId, name);
    }
}
