package com.bird.framework.system.service;

import com.bird.framework.system.entity.QResource;
import com.bird.framework.system.entity.Resource;
import com.bird.framework.system.repository.ResourceRepo;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<Resource> findByRole(Long roleId) {
        return resourceRepo.findByRolesId(roleId);
    }


    public void save(Resource resource) {
        resourceRepo.save(resource);
    }

    public Page<Resource> page(Long tenantId, Long roleId, int pageNo, int pageSize) {
        QResource qResource = QResource.resource;
        BooleanExpression booleanExpression = null;
        if (tenantId != null) {
            if (booleanExpression == null) {
                booleanExpression = qResource.tenant.id.eq(tenantId);
            } else {
                booleanExpression.and(qResource.tenant.id.eq(tenantId));
            }
        }
        if (roleId != null) {
            if (booleanExpression == null) {
                booleanExpression = qResource.roles.any().id.eq(roleId);
            } else {
                booleanExpression.and(qResource.roles.any().id.eq(roleId));
            }
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Resource> resources = resourceRepo.findAll(booleanExpression, pageable);

        return resources;
    }
}
