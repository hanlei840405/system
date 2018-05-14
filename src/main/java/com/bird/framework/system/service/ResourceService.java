package com.bird.framework.system.service;

import com.bird.framework.system.entity.Resource;
import com.bird.framework.system.repository.ResourceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepo resourceRepo;

    public Resource get(Long id) {
        return resourceRepo.getOne(id);
    }

    public Resource getByCode(String code) {
        return resourceRepo.findByCode(code);
    }

    public List<Resource> findByUserId(Long roleId) {
        return resourceRepo.findByRolesId(roleId);
    }

    public List<Resource> findByUserId(Long tenantId, Long roleId) {
        return resourceRepo.findByTenantIdAndRolesId(tenantId, roleId);
    }

    public void save(Resource resource) {
        resourceRepo.save(resource);
    }
}
